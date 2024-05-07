package nl.rijksoverheid.mgo.feature.localisation.stored

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.HealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class StoredHealthCareProvidersScreenViewModel
    @Inject
    constructor(
        private val healthCareProviderRepository: HealthCareProviderRepository,
    ) : ViewModel() {
        private val _viewState =
            healthCareProviderRepository.storedHealthCareProvidersFlow.map { providers ->
                StoredHealthCareProvidersScreenViewState(providers = providers)
            }
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, StoredHealthCareProvidersScreenViewState.initialState)

        fun delete(provider: HealthCareProvider) {
            viewModelScope.launch {
                healthCareProviderRepository.delete(provider)
            }
        }
    }
