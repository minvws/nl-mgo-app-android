package nl.rijksoverheid.mgo.feature.localisation.organizationSearch.automatic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class OrganizationAutomaticSearchScreenViewModel
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
    ) : ViewModel() {
        private val _navigation = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigation = _navigation.asSharedFlow()

        private val initialState = OrganizationAutomaticSearchScreenViewState.initialState
        private val _viewState: MutableStateFlow<OrganizationAutomaticSearchScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            getSearchResults()
        }

        fun getSearchResults() {
            viewModelScope.launch {
                _viewState.update { viewState -> viewState.copy(loading = true, results = listOf(), error = null) }
                organizationRepository
                    .search(name = "test", city = "test")
                    .catch { error ->
                        _viewState.update { viewState -> viewState.copy(loading = false, error = error) }
                    }
                    .collectLatest { results ->
                        _viewState.update { viewState -> viewState.copy(loading = false, results = results, error = null) }
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
    }
