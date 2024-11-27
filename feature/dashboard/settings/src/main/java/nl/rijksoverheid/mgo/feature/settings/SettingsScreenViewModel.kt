package nl.rijksoverheid.mgo.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class SettingsScreenViewModel
    @Inject
    constructor(private val featureToggleRepository: FeatureToggleRepository) : ViewModel() {
        private val initialState =
            SettingsScreenViewState.initialState(
                featureToggleFlagSecure =
                    FeatureToggle(
                        id = FeatureToggleId.FlagSecureEnabled,
                        enabled = featureToggleRepository.get(FeatureToggleId.FlagSecureEnabled),
                    ),
            )
        private val _viewState: MutableStateFlow<SettingsScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                launch {
                    featureToggleRepository.observe(FeatureToggleId.FlagSecureEnabled).collectLatest { enabled ->
                        _viewState.update { viewState ->
                            viewState.copy(
                                featureToggleFlagSecure =
                                    FeatureToggle(
                                        id = FeatureToggleId.FlagSecureEnabled,
                                        enabled = enabled,
                                    ),
                            )
                        }
                    }
                }
            }
        }

        fun onFeatureToggleChanged(
            id: FeatureToggleId,
            enabled: Boolean,
        ) {
            viewModelScope.launch {
                featureToggleRepository.set(id, enabled)
            }
        }
    }
