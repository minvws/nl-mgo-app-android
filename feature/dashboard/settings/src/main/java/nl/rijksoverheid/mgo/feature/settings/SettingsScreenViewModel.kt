package nl.rijksoverheid.mgo.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class SettingsScreenViewModel
    @Inject
    constructor(
        private val featureToggleRepository: FeatureToggleRepository,
        private val organizationRepository: OrganizationRepository,
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
        @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
    ) : ViewModel() {
        private val initialState =
            SettingsScreenViewState.initialState(
                featureToggleSkipPin =
                    FeatureToggle(
                        id = FeatureToggleId.SkipPin,
                        enabled = featureToggleRepository.get(FeatureToggleId.SkipPin),
                    ),
                featureToggleFlagSecure =
                    FeatureToggle(
                        id = FeatureToggleId.FlagSecureEnabled,
                        enabled = featureToggleRepository.get(FeatureToggleId.FlagSecureEnabled),
                    ),
                featureToggleAutomaticLocalisation =
                    FeatureToggle(
                        id = FeatureToggleId.AutomaticLocalisation,
                        enabled = featureToggleRepository.get(FeatureToggleId.AutomaticLocalisation),
                    ),
            )
        private val _viewState: MutableStateFlow<SettingsScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        private val _navigateToOnboarding = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateToOnboarding = _navigateToOnboarding.asSharedFlow()

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

                launch {
                    featureToggleRepository.observe(FeatureToggleId.SkipPin).collectLatest { enabled ->
                        _viewState.update { viewState ->
                            viewState.copy(
                                featureToggleSkipPin =
                                    FeatureToggle(
                                        id = FeatureToggleId.SkipPin,
                                        enabled = enabled,
                                    ),
                            )
                        }
                    }
                }

                launch {
                    featureToggleRepository.observe(FeatureToggleId.AutomaticLocalisation).collectLatest { enabled ->
                        _viewState.update { viewState ->
                            viewState.copy(
                                featureToggleAutomaticLocalisation =
                                    FeatureToggle(
                                        id = FeatureToggleId.AutomaticLocalisation,
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

        fun resetApp() {
            viewModelScope.launch {
                organizationRepository.deleteAll()
                keyValueStore.clear()
                secureKeyValueStore.clear()
                _navigateToOnboarding.tryEmit(Unit)
            }
        }
    }
