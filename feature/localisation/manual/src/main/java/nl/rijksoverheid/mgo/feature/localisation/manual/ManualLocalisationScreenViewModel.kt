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
import nl.rijksoverheid.mgo.component.organization.Organization
import nl.rijksoverheid.mgo.data.healthCategories.GetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SearchScreenViewModel
  @Inject
  constructor(
    private val getDataSetsFromDisk: GetDataSetsFromDisk,
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
              val organizations = organizationRepository.search(query).first()
              val supportedDataServiceIds = getDataSetsFromDisk().map { it.id }
              _viewState.update { viewState ->
                viewState.copy(
                  loading = false,
                  error = false,
                  organizations =
                    organizations.map { organization ->
                      organization.toUi(supportedDataServiceIds)
                    },
                )
              }
            }
          } catch (error: Throwable) {
            _viewState.update { viewState -> viewState.copy(loading = false, error = true) }
          }
        }
    }

    fun add(organization: Organization) {
      viewModelScope.launch {
        val supportedDataServiceIds = getDataSetsFromDisk().map { it.id }
//        val mgoOrganization = searchResult.toMgoOrganization(supportedDataServiceIds)
//        organizationRepository.save(mgoOrganization)
//        _navigateToDashboard.tryEmit(Unit)
      }
    }

    private suspend fun Organization.toUi(supportedDataServiceIds: List<String>): OrganizationUi =
      OrganizationUi(
        organization = this,
        supported = false,
      )
  }
