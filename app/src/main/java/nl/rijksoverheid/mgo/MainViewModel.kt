package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasSeenPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen
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
        private val hasSeenOnboarding: HasSeenOnboarding,
        private val hasSeenPinCode: HasSeenPinCode,
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
                hasPinCode() -> {
                    DashboardNavigationScreen.Start.getRoute()
                }

                hasSeenOnboarding.invoke() -> {
                    PinCodeNavigationScreen.Start.getNavigationRoute()
                }

                else -> {
                    OnboardingNavigationScreen.Start.getNavigationRoute()
                }
            }
        }

        fun hasPinCode(): Boolean {
            return hasSeenPinCode.invoke()
        }

        fun refreshConfig() {
            viewModelScope.launch {
                configRepository.refresh()
            }
        }
    }
