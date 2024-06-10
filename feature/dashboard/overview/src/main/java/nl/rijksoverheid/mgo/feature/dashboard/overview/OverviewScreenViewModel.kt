package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.HealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class OverviewScreenViewModel
    @Inject
    constructor(healthCareProviderRepository: HealthCareProviderRepository) : ViewModel() {
        private val initialViewState =
            OverviewScreenViewState.initialState(
                providers =
                    runBlocking {
                        healthCareProviderRepository.get()
                    },
            )
        private val _viewState = MutableStateFlow(initialViewState)
        val viewState =
            combine(_viewState, healthCareProviderRepository.storedHealthCareProvidersFlow) { viewState, providers ->
                OverviewScreenViewState(name = viewState.name, providers = listOf(TEST_HEALTH_CARE_PROVIDER))
            }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
    }
