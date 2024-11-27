package nl.rijksoverheid.mgo.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.featureToggles
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
        private val _featureToggleStates =
            combine(
                featureToggleRepository.observe(FeatureToggleId.SkipPin),
                featureToggleRepository.observe(FeatureToggleId.FlagSecure),
                featureToggleRepository.observe(FeatureToggleId.AutomaticLocalisation),
            ) { skipPin, flagSecure, automaticLocalisation ->
                featureToggles.map { featureToggle ->
                    val enabled =
                        when (featureToggle.id) {
                            FeatureToggleId.AutomaticLocalisation -> automaticLocalisation
                            FeatureToggleId.FlagSecure -> flagSecure
                            FeatureToggleId.SkipPin -> skipPin
                        }
                    FeatureToggleWithState(featureToggle = featureToggle, enabled = enabled)
                }
            }
        val featureToggleStates = _featureToggleStates.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

        private val _navigateToOnboarding = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateToOnboarding = _navigateToOnboarding.asSharedFlow()

        fun onFeatureToggleChanged(
            toggle: FeatureToggle,
            enabled: Boolean,
        ) {
            viewModelScope.launch {
                featureToggleRepository.set(toggle, enabled)
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
