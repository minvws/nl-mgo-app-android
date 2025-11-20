package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class OrganizationListAutomaticScreenViewModel
  @Inject
  constructor(
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val organizationRepository: OrganizationRepository,
    private val getDataSetsFromDisk: GetDataSetsFromDisk,
  ) : ViewModel() {
    private val _navigation = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    private val initialState = OrganizationListAutomaticScreenViewState.initialState
    private val _viewState: MutableStateFlow<OrganizationListAutomaticScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    init {
      getSearchResults()
    }

    fun getSearchResults() {
      viewModelScope.launch(ioDispatcher) {
        _viewState.value = _viewState.value.copy(loading = true, results = listOf(), error = null)
        val supportedDataServiceIds = getDataSetsFromDisk().map { it.id }
        organizationRepository
          .searchDemo(supportedDataServiceIds)
          .catch { error ->
            _viewState.value = _viewState.value.copy(loading = false, error = error)
          }.collectLatest { results ->
            _viewState.value = _viewState.value.copy(loading = false, results = results, error = null)
          }
      }
    }

    fun updateOrganization(
      organization: MgoOrganization,
      added: Boolean,
    ) {
      _viewState.update { viewState ->
        viewState.copy(
          results =
            viewState.results.map { result ->
              if (result == organization) {
                result.copy(added = added)
              } else {
                result
              }
            },
        )
      }
    }

    fun updateOrganizations() {
      viewModelScope.launch(ioDispatcher) {
        val checkedOrganizations = _viewState.value.results.filter { organization -> organization.added }
        val unCheckedOrganizations = _viewState.value.results.filter { organization -> !organization.added }

        for (organization in checkedOrganizations) {
          organizationRepository.save(organization)
        }

        for (organization in unCheckedOrganizations) {
          organizationRepository.delete(organization.id)
        }

        _navigation.tryEmit(Unit)
      }
    }
  }
