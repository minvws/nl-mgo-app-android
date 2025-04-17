package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject

/**
 * The [ViewModel] for [OrganizationListAutomaticSearchScreen].
 *
 * @param organizationRepository The [OrganizationRepository] to get and update health care providers.
 */
@HiltViewModel
internal class OrganizationListAutomaticScreenViewModel
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
  ) : ViewModel() {
    private val _navigation = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    private val initialState = OrganizationListAutomaticScreenViewState.initialState
    private val _viewState: MutableStateFlow<OrganizationListAutomaticScreenViewState> = MutableStateFlow(initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    init {
      getSearchResults()
    }

    /**
     * Get health care providers and reflect the result in the UI.
     */
    fun getSearchResults() {
      viewModelScope.launch {
        _viewState.value = _viewState.value.copy(loading = true, results = listOf(), error = null)
        organizationRepository
          .searchDemo()
          .catch { error ->
            _viewState.value = _viewState.value.copy(loading = false, error = error)
          }
          .collectLatest { results ->
            _viewState.value = _viewState.value.copy(loading = false, results = results, error = null)
          }
      }
    }

    /**
     * Call to change the checkbox state for a card displayed in the UI.
     *
     * @param organization The [MgoOrganization] to change the state for.
     * @param added If the health care provider was added.
     */
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

    /**
     * Call to save or delete organizations based on if the checkbox was checked.
     */
    fun updateOrganizations() {
      viewModelScope.launch {
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
