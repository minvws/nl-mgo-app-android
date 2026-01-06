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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.error.ErrorBannerState
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
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
    private val getErrorBanner: GetErrorBanner,
    private val observeFhirResponses: ObserveFhirResponses,
    private val listItemStateMapper: ListItemStateMapper,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        @Assisted("category") category: HealthCategoryGroup.HealthCategory,
        @Assisted("filterOrganization") filterOrganization: MgoOrganization? = null,
      ): HealthCategoryScreenViewModel
    }

    private val initialState =
      HealthCategoryScreenViewState(
        category = category,
        listItemsState = HealthCategoryScreenViewState.ListItemsState.Loading,
        banner = ErrorBannerState.Loading,
      )
    private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    private val _openPdfViewer = MutableSharedFlow<PdfViewerState>(extraBufferCapacity = 2)
    val openPdfViewer = _openPdfViewer.asSharedFlow()

    init {
      viewModelScope.launch(ioDispatcher) {
        launch {
          observeListItemsState()
        }
        launch {
          observeErrorBanner()
        }
      }
    }

    private suspend fun observeListItemsState() {
      // Get the organizations that we need to get fhir responses from
      val organizations = if (filterOrganization == null) organizationRepository.get() else listOf(filterOrganization)

      // Get the fhir responses
      val fhirResponses = observeFhirResponses(organizations = organizations, categories = listOf(category))

      // Observe fhir responses
      fhirResponses.collectLatest { responses ->
        // Map fhir responses to list item state
        val listItemState = listItemStateMapper(responses = responses, category = category)

        // Update view state
        _viewState.update { viewState -> viewState.copy(listItemsState = listItemState) }
      }
    }

    private suspend fun observeErrorBanner() {
      // Get the organizations that we need to get fhir responses from
      val organizations = if (filterOrganization == null) organizationRepository.get() else listOf(filterOrganization)

      // Observe error banner
      getErrorBanner.invoke(categories = listOf(category), organizations = organizations).collectLatest { banner ->

        // Update view state
        _viewState.update { viewState -> viewState.copy(banner = banner) }
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
