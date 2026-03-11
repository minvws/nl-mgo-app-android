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
          try {
            if (query.length <= 2) {
              _viewState.update { viewState -> viewState.copy(organizations = listOf(), loading = false) }
            } else {
              _viewState.update { viewState -> viewState.copy(loading = true) }
              delay(500)
              val organizations = organizationRepository.search(query = query, context = ioDispatcher).first()
              _viewState.update { viewState ->
                viewState.copy(
                  loading = false,
                  error = false,
                  organizations = organizations,
                )
              }
            }
          } catch (error: Throwable) {
            _viewState.update { viewState -> viewState.copy(loading = false, error = true) }
          }
        }
    }

    fun add(organization: MgoOrganization) {
      viewModelScope.launch(ioDispatcher) {
        organizationRepository.save(organization.id)
        _navigateToDashboard.tryEmit(Unit)
      }
    }
  }
