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
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfHealthCategory
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type.GetHealthCategoryScreenType
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoryScreenViewModel.Factory::class)
internal class HealthCategoryScreenViewModel
  @AssistedInject
  constructor(
    @Assisted("category") private val category: HealthCategoryGroup.HealthCategory,
    @Assisted("filterOrganization") private val filterOrganization: MgoOrganization? = null,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val organizationRepository: OrganizationRepository,
    private val getErrorBanner: GetErrorBanner,
    private val createPdfHealthCategory: CreatePdfHealthCategory,
    private val observeListItemsState: ObserveListItemsState,
    private val retryFailedRequests: RetryFailedRequests,
    private val onClearScreen: OnClearScreen,
    getHealthCategoryScreenType: GetHealthCategoryScreenType,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        @Assisted("category") category: HealthCategoryGroup.HealthCategory,
        @Assisted("filterOrganization") filterOrganization: MgoOrganization? = null,
      ): HealthCategoryScreenViewModel
    }

    private val type = getHealthCategoryScreenType(category)

    private val initialState =
      HealthCategoryScreenViewState(
        category = category,
        listItemsState = HealthCategoryScreenViewState.ListItemsState.Loading,
        banner = ErrorBannerState.Loading,
        type = type,
      )
    private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    private val _openPdfViewer = MutableSharedFlow<PdfViewerState>(extraBufferCapacity = 2)
    val openPdfViewer = _openPdfViewer.asSharedFlow()

    init {
      viewModelScope.launch(ioDispatcher) {
        val organizations = getOrganizations()

        // Observe list items
        launch {
          observeListItemsState(type = type, category = category, organizations = organizations).collectLatest { listItemState ->
            _viewState.update { viewState -> viewState.copy(listItemsState = listItemState) }
          }
        }

        // Observe error banner
        launch {
          getErrorBanner.invoke(categories = listOf(category), organizations = organizations).collectLatest { banner ->
            // Update view state
            _viewState.update { viewState -> viewState.copy(banner = banner) }
          }
        }
      }
    }

    fun retry() {
      viewModelScope.launch(ioDispatcher) {
        val organizations = getOrganizations()
        retryFailedRequests.invoke(category = category, organizations = organizations)
      }
    }

    fun generatePdf() {
      viewModelScope.launch(ioDispatcher) {
        // Communicate to UI that pdf is being created
        _openPdfViewer.tryEmit(PdfViewerState.Loading)

        // Create pdf
        val mgoResources = _viewState.value.listItemsState.getMgoResources()
        val file = createPdfHealthCategory(mgoResources = mgoResources, category = category)

        // Communicate to UI that pdf has been created
        _openPdfViewer.tryEmit(PdfViewerState.Loaded(file))
      }
    }

    override fun onCleared() {
      super.onCleared()
      clear()
    }

    @VisibleForTesting
    suspend fun getOrganizations() =
      if (filterOrganization ==
        null
      ) {
        organizationRepository.getSaved(currentCoroutineContext()).first()
      } else {
        listOf(filterOrganization)
      }

    @VisibleForTesting
    fun clear() {
      onClearScreen.invoke()
    }
  }
