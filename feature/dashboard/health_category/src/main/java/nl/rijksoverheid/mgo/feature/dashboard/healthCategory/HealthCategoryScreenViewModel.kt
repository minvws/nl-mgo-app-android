package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.VisibleForTesting
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
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfForHealthCategories
import javax.inject.Named

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

    private val initialState =
      HealthCategoryScreenViewState(category = category, showErrorBanner = false, listItemsState = HealthCategoryScreenViewState.ListItemsState.Loading)
    private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    private val _openPdfViewer = MutableSharedFlow<PdfViewerState>(extraBufferCapacity = 2)
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
                it.id == filterOrganization.id
              }
            }
          }

        organizationsFlow.collectLatest { organizations ->
          // Get all the fhir responses for this category that we can observe
          val fhirResponseFlows =
            organizations
              .map { organization ->
                val endpoints = getEndpointsForHealthCategory(category = category, organization = organization)
                endpoints.map { endpoint ->
                  fhirRepository.observe(
                    organizationId = organization.id,
                    dataServiceId = endpoint.dataServiceId,
                    endpointId = endpoint.endpointId,
                  )
                }
              }.flatten()

          if (fhirResponseFlows.isEmpty()) {
            _viewState.update { viewState -> viewState.copy(listItemsState = HealthCategoryScreenViewState.ListItemsState.NoData) }
          } else {
            // Observe the fhir responses
            combine(fhirResponseFlows) { responses -> responses.toList() }.collectLatest { responses ->
              // True if not all data was fetched
              val hasError = responses.filterIsInstance<FhirResponse.Error>().isNotEmpty()

              // Get all the responses that are successful
              val successResponses = responses.filterIsInstance<FhirResponse.Success>()

              // If there is not data show empty state
              val allEmpty = responses.filterIsInstance<FhirResponse.Success>().all { response -> response.isEmpty }
              if (allEmpty) {
                _viewState.update { viewState ->
                  viewState.copy(listItemsState = HealthCategoryScreenViewState.ListItemsState.NoData)
                }
              } else {
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
      }
    }

    fun retry() {
      viewModelScope.launch(ioDispatcher) {
        val organizations = organizationRepository.get()
        for (organization in organizations) {
          val endpoints = getEndpointsForHealthCategory(category = category, organization = organization)
          for (endpoint in endpoints) {
            val request =
              FhirRequest(
                organizationId = organization.id,
                medmijId = organization.medMijId,
                dataServiceId = endpoint.dataServiceId,
                endpointId = endpoint.endpointId,
                endpointPath = endpoint.endpointPath,
                resourceEndpoint = endpoint.resourceEndpoint,
                fhirVersion = endpoint.fhirVersion,
                url = "$dvaApiBaseUrl/fhir${endpoint.endpointPath}",
              )

            fhirRepository.fetch(
              request = request,
              forceRefresh = true,
            )
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
      clear()
    }

    @VisibleForTesting
    fun clear() {
      mgoResourceStore.clear()
    }
  }
