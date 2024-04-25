package nl.rijksoverheid.mgo.feature.localisation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.HealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AddedHealthCareOverviewScreenViewModel
    @Inject
    constructor(
        private val healthCareProviderRepository: HealthCareProviderRepository,
    ) : ViewModel() {
        private val _viewState =
            healthCareProviderRepository.storedHealthCareProvidersFlow.map { providers ->
                AddedHealthCareOverviewScreenViewState(providers = providers)
            }
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, AddedHealthCareOverviewScreenViewState.initialState)

        private val _navigateBack = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateBack = _navigateBack.asSharedFlow()

        fun delete(provider: HealthCareProvider) {
            viewModelScope.launch {
                healthCareProviderRepository.delete(provider)

                // If all providers have been removed, navigate back to previous screen
                val amountOfProvidersAfterDelete = viewState.value.providers.size
                if (amountOfProvidersAfterDelete == 0) {
                    _navigateBack.tryEmit(Unit)
                }
            }
        }
    }
