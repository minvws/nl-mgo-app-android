package nl.rijksoverheid.mgo.feature.organization.problems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.concern.ConcernRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProblemsScreenViewModel.Factory::class)
class ProblemsScreenViewModel
    @AssistedInject
    constructor(
        @Assisted provider: MgoOrganization,
        private val concernRepository: ConcernRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(provider: MgoOrganization): ProblemsScreenViewModel
        }

        private val initialState = ProblemsScreenViewState.initialState
        private val _viewState: MutableStateFlow<ProblemsScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                _viewState.update { viewState -> viewState.copy(loading = true) }
                concernRepository
                    .getConcerns(provider.resourceEndpoint)
                    .onSuccess { concerns -> _viewState.update { viewState -> viewState.copy(loading = false, concerns = concerns) } }
                    .onFailure { error -> _viewState.update { viewState -> viewState.copy(loading = false, error = error) } }
            }
        }
    }
