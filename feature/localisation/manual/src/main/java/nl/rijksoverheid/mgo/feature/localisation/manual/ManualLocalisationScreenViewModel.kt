package nl.rijksoverheid.mgo.feature.localisation.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ManualLocalisationScreenViewModel
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    private var searchJob: Job? = null

    private val _viewState = MutableStateFlow(ManualLocalisationScreenViewState.initialState)
    val viewState = _viewState.asStateFlow()

    private val _navigateToDashboard = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToDashboard = _navigateToDashboard.asSharedFlow()

    fun search(query: String) {
      searchJob?.cancel()
      searchJob =
        viewModelScope.launch(ioDispatcher) {
          // Only start searching if the user types more than two characters
          if (query.length <= 2) {
            _viewState.update { viewState -> viewState.copy(loading = false, organizations = null, error = false) }
            return@launch
          }

          // Show loading state
          _viewState.update { viewState -> viewState.copy(loading = true, organizations = null, error = false) }

          // Delay so that UI does not get updated on every keypress
          delay(500)

          // Make sure organizations in the local database are up to date
          val success = organizationRepository.sync()

          if (success) {
            // Search for organizations
            val organizations = organizationRepository.search(query = query, context = ioDispatcher).first()
            _viewState.update { viewState ->
              viewState.copy(
                loading = false,
                error = false,
                organizations = organizations,
              )
            }
          } else {
            // If syncing of organizations in database failed, show error state
            _viewState.update { viewState -> viewState.copy(loading = false, organizations = null, error = true) }
          }
        }
    }

    fun add(organization: MgoOrganization) {
      viewModelScope.launch {
        organizationRepository.save(organization.id)
        _navigateToDashboard.tryEmit(Unit)
      }
    }
  }
