package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.GetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Named

@HiltViewModel(assistedFactory = OrganizationListManualScreenViewModel.Factory::class)
internal class OrganizationListManualScreenViewModel
  @AssistedInject
  constructor(
    @Assisted("name") private val name: String,
    @Assisted("city") private val city: String,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val organizationRepository: OrganizationRepository,
    private val getDataSetsFromDisk: GetDataSetsFromDisk,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        @Assisted("name") name: String,
        @Assisted("city") city: String,
      ): OrganizationListManualScreenViewModel
    }

    private val _navigation = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    private val initialState =
      OrganizationListManualScreenViewState.initialState(name = name, city = city)
    private val _viewState: MutableStateFlow<OrganizationListManualScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    init {
      getSearchResults()
    }

    fun getSearchResults() {
      viewModelScope.launch(ioDispatcher) {
        _viewState.update { viewState -> viewState.copy(loading = true, results = listOf(), error = null) }
        val supportedDataServiceIds = getDataSetsFromDisk().map { it.id }
        organizationRepository
          .search(name = name, city = city, supportedDataServiceIds = supportedDataServiceIds)
          .catch { error ->
            _viewState.update { viewState -> viewState.copy(loading = false, error = error) }
          }.collectLatest { results ->
            _viewState.update { viewState -> viewState.copy(loading = false, results = results, error = null) }
          }
      }
    }

    fun addOrganization(provider: MgoOrganization) {
      viewModelScope.launch {
        organizationRepository.save(provider)
        _navigation.tryEmit(Unit)
      }
    }
  }
