package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = OrganizationListManualScreenViewModel.Factory::class)
internal class OrganizationListManualScreenViewModel
    @AssistedInject
    constructor(
        @Assisted("name") private val name: String,
        @Assisted("city") private val city: String,
        private val organizationRepository: OrganizationRepository,
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
            viewModelScope.launch {
                _viewState.update { viewState -> viewState.copy(loading = true, results = listOf(), error = null) }
                organizationRepository
                    .search(name = name, city = city)
                    .catch { error ->
                        _viewState.update { viewState -> viewState.copy(loading = false, error = error) }
                    }
                    .collectLatest { results ->
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
