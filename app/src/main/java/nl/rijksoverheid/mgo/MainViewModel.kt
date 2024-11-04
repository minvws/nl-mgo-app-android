package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainViewModel
    @Inject
    constructor(
        val showDeviceRootedDialog: ShowDeviceRootedDialog,
        val hasPinCode: HasPinCode,
        private val hasSeenOnboarding: HasSeenOnboarding,
        private val configRepository: ConfigRepository,
    ) : ViewModel() {
        val configStateFlow =
            configRepository.configStateFlow.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ConfigState.NoAction,
            )

        fun getStartDestination(): Any {
            return when {
                hasSeenOnboarding.invoke() -> {
                    OnboardingNavigation.Root
                }
                else -> {
                    OnboardingNavigation.Root
                }
            }
        }

        fun refreshConfig() {
            viewModelScope.launch {
                configRepository.refresh()
            }
        }
    }
