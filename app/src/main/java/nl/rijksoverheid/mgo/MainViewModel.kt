package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigationScreen
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeNavigationScreen
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

        fun getStartDestination(): String {
            return when {
                hasSeenOnboarding.invoke() -> {
                    PinCodeNavigationScreen.Start.getNavigationRoute()
                }

                else -> {
                    OnboardingNavigationScreen.Start.getNavigationRoute()
                }
            }
        }

        fun refreshConfig() {
            viewModelScope.launch {
                configRepository.refresh()
            }
        }
    }
