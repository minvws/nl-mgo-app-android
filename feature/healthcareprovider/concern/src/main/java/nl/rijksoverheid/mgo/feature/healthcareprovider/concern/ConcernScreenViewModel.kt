package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.concern.ConcernRepository
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ConcernScreenViewModel.Factory::class)
class ConcernScreenViewModel
    @AssistedInject
    constructor(
        @Assisted provider: HealthCareProvider,
        private val concernRepository: ConcernRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(provider: HealthCareProvider): ConcernScreenViewModel
        }

        private val initialState = ConcernScreenViewState.initialState
        private val _viewState: MutableStateFlow<ConcernScreenViewState> = MutableStateFlow(initialState)
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
