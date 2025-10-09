package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfForHealthCategories
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import javax.inject.Named

/**
 * The [ViewModel] for [HealthCategoryScreen].
 *
 * @param category The [HealthCareCategoryId] to determine which health care data falls into this category.
 * @param filterOrganization If not null, will observe health care data for this organization. If null will observe for all added
 * organizations.
 * @param context Application context.
 * @param organizationRepository The [OrganizationRepository] to fetch the added organizations.
 * @param healthCareDataStatesRepository The [HealthCareDataStatesRepository] that is responsible for fetching the health care data.
 * @param mgoResourceRepository The [MgoResourceRepository] that is used to filter out resources so that only the resources are shown
 * that we want to show.
 * @param uiSchemaMapper The [UiSchemaMapper] to get models for displaying the health care data.
 * @param createPdf The [CreatePdfForHealthCategories] to generate a presentable pdf.
 */
@HiltViewModel(assistedFactory = HealthCategoryScreenViewModel.Factory::class)
internal class HealthCategoryScreenViewModel
  @AssistedInject
  constructor(
    @Assisted("category") private val category: HealthCategoryGroup.HealthCategory,
    @Assisted("filterOrganization") private val filterOrganization: MgoOrganization? = null,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    private val organizationRepository: OrganizationRepository,
    private val createPdf: CreatePdfForHealthCategories,
    private val fhirRepository: FhirRepository,
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
    private val listItemGroupMapper: ListItemGroupMapper,
    private val mgoResourceStore: MgoResourceStore,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        @Assisted("category") category: HealthCategoryGroup.HealthCategory,
        @Assisted("filterOrganization") filterOrganization: MgoOrganization? = null,
      ): HealthCategoryScreenViewModel
    }

    private val initialState = HealthCategoryScreenViewState.initialState(category)
    private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    private val _openPdfViewer = MutableSharedFlow<PdfViewerState>(extraBufferCapacity = 1)
    val openPdfViewer = _openPdfViewer.asSharedFlow()

    init {
      viewModelScope.launch(ioDispatcher) {
        val organizationsFlow =
          if (filterOrganization == null) {
            // If we do not want to filter on a specific organization, observe all stored organizations
            organizationRepository.storedOrganizationsFlow
          } else {
            // If we want to filter on a specific organization, filter on that one
            organizationRepository.storedOrganizationsFlow.map { organizations ->
              organizations.filter {
                it.id ==
                  filterOrganization.id
              }
            }
          }

        organizationsFlow.collectLatest { organizations ->
          // Get all the fhir responses for this category that we can observe
          val fhirResponseFlows =
            organizations
              .map { organization ->
                val dataSetIds = organization.dataServices.map { it.id }
                val endpointsForCategory = getEndpointsForHealthCategory(category = category, filterDataSetIds = dataSetIds).map { it.endpoints }.flatten()
                organization.dataServices.map { dataService ->
                  endpointsForCategory.map { endpoint ->
                    fhirRepository.observe(
                      organizationId = organization.id,
                      dataServiceId = dataService.id,
                      endpointId = endpoint.id,
                    )
                  }
                }
              }.flatten()
              .flatten()

          // Observe the fhir responses
          combine(fhirResponseFlows) { responses -> responses.toList() }.collectLatest { responses ->
            // True if not all data was fetched
            val hasError = responses.filterIsInstance<FhirResponse.Error>().isNotEmpty()

            // Get all the responses that are successful
            val successResponses = responses.filterIsInstance<FhirResponse.Success>()

            // Create list items from them to show in the UI
            val listItemGroups = listItemGroupMapper.invoke(category = category, fhirResponses = successResponses)

            // Store all mgo resources in a store, because we need them in the ui schema screen
            val mgoResources = listItemGroups.map { group -> group.items.map { item -> item.mgoResource } }.flatten()
            for (mgoResource in mgoResources) {
              mgoResourceStore.store(mgoResource)
            }

            // Update view state
            _viewState.update { viewState ->
              viewState.copy(listItemsState = HealthCategoryScreenViewState.ListItemsState.Loaded(listItemGroups), showErrorBanner = hasError)
            }
          }
        }
      }
    }

    fun retry() {
      viewModelScope.launch(ioDispatcher) {
        val organizations = organizationRepository.get()
        for (organization in organizations) {
          val dataSetIds = organization.dataServices.map { it.id }
          val endpointsWithDataSet = getEndpointsForHealthCategory(category = category, filterDataSetIds = dataSetIds)
          for (endpointWithDataSet in endpointsWithDataSet) {
            for (endpoint in endpointWithDataSet.endpoints) {
              for (dataService in organization.dataServices) {
                fhirRepository.fetch(
                  organizationId = organization.id,
                  dataServiceId = dataService.id,
                  endpointId = endpoint.id,
                  resourceEndpoint = dataService.resourceEndpoint,
                  fhirVersion = FhirVersion.valueOf(endpointWithDataSet.dataSet.fhirVersion),
                  url = "$dvaApiBaseUrl/fhir${endpoint.url}",
                )
              }
            }
          }
        }
      }
    }

    fun generatePdf() {
      viewModelScope.launch(ioDispatcher) {
        _openPdfViewer.tryEmit(PdfViewerState.Loading)
        val listItemGroups = (_viewState.value.listItemsState as? HealthCategoryScreenViewState.ListItemsState.Loaded)?.listItemsGroup ?: listOf()
        val file =
          createPdf.invoke(
            category = category,
            listItemGroups = listItemGroups,
          )
        _openPdfViewer.tryEmit(PdfViewerState.Loaded(file))
      }
    }

    override fun onCleared() {
      super.onCleared()
      mgoResourceStore.clear()
    }
  }
