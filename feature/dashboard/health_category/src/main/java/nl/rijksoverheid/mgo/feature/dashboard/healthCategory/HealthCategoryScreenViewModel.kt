package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.error.ErrorBannerState
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.pdf.viewer.PdfViewerState
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfHealthCategory
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.GroupedHealthUiSchemas
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoryScreenViewModel.Factory::class)
internal class HealthCategoryScreenViewModel
  @AssistedInject
  constructor(
    @Assisted("category") private val category: HealthCategoryGroup.HealthCategory,
    @Assisted("filterOrganization") private val filterOrganization: MgoOrganization? = null,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val organizationRepository: OrganizationRepository,
    private val fhirRepository: FhirRepository,
    private val mgoResourceStore: MgoResourceStore,
    private val getErrorBanner: GetErrorBanner,
    private val observeFhirResponses: ObserveFhirResponses,
    private val listItemStateMapper: ListItemStateMapper,
    private val getRequests: GetRequests,
    private val mgoResourceParser: MgoResourceParser,
    private val createPdfHealthCategory: CreatePdfHealthCategory,
    private val uiSchemaParser: UiSchemaParser,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
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
      val organizations = if (filterOrganization == null) organizationRepository.getSaved(currentCoroutineContext()).first() else listOf(filterOrganization)

      // Get the fhir responses
      val fhirResponses = observeFhirResponses(organizations = organizations, categories = listOf(category))

      // Observe fhir responses
      fhirResponses.collectLatest { responses ->

        // Create mgo resources
        val mgoResources =
          responses
            .filterIsInstance<FhirResponse.Success>()
            .flatMap { response ->
              mgoResourceParser(
                fhirResponse = mgoByteArrayStorage.get(response.cacheKey)?.toString(Charsets.UTF_8) ?: "{}",
                fhirVersion = response.request.fhirVersion,
                organizationId = response.request.organizationId,
                organizationName = response.request.organizationName,
              )
            }

        // Cache mgo resources
        for (mgoResource in mgoResources) {
          mgoResourceStore.store(mgoResource)
        }

        // Map fhir responses to list item state
        val listItemState = listItemStateMapper(responses = responses, mgoResources = mgoResources, category = category)

        // Update view state
        _viewState.update { viewState -> viewState.copy(listItemsState = listItemState) }
      }
    }

    private suspend fun observeErrorBanner() {
      // Get the organizations that we need to get fhir responses from
      val organizations = if (filterOrganization == null) organizationRepository.getSaved(currentCoroutineContext()).first() else listOf(filterOrganization)

      // Observe error banner
      getErrorBanner.invoke(categories = listOf(category), organizations = organizations).collectLatest { banner ->

        // Update view state
        _viewState.update { viewState -> viewState.copy(banner = banner) }
      }
    }

    fun retry() {
      viewModelScope.launch(ioDispatcher) {
        // Get requests
        val organizations = if (filterOrganization == null) organizationRepository.getSaved(coroutineContext).first() else listOf(filterOrganization)
        val requests = getRequests(organizations = organizations, categories = listOf(category))

        // Get responses that failed
        val failedResponses =
          fhirRepository
            .observe()
            .first()
            .filterIsInstance<FhirResponse.Error>()
            .filter { response -> requests.contains(response.request) }

        // Map to requests
        val failedRequests = failedResponses.map { response -> response.request }

        // Retry
        fhirRepository.retry(failedRequests)
      }
    }

    fun generatePdf() {
      viewModelScope.launch(ioDispatcher) {
        // Communicate to UI that pdf is being created
        _openPdfViewer.tryEmit(PdfViewerState.Loading)

        // Create pdf
        val mgoResources = _viewState.value.listItemsState.getMgoResources()
        val groupedMgoResources = mgoResources.groupBySubCategory(subcategories = category.subcategories)
        val uiSchemas =
          groupedMgoResources.map {
            val uiSchemas = it.value.map { mgoResource -> uiSchemaParser.getSummary(mgoResource.json, organizationName = mgoResource.organizationName) }
            GroupedHealthUiSchemas(heading = it.key.heading, uiSchemas = uiSchemas)
          }
        val file = createPdfHealthCategory(uiSchemas = uiSchemas, category = category)

        // Communicate to UI that pdf has been created
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
