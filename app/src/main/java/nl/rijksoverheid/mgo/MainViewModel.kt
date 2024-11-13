package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.lock.CheckAppLock
import nl.rijksoverheid.mgo.lock.SaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeLoginNavigation
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainViewModel
    @Inject
    constructor(
        val showDeviceRootedDialog: ShowDeviceRootedDialog,
        private val checkAppLock: CheckAppLock,
        private val saveClosedAppTimestamp: SaveClosedAppTimestamp,
        private val hasPinCode: HasPinCode,
        private val hasSeenOnboarding: HasSeenOnboarding,
        private val configRepository: ConfigRepository,
    ) : ViewModel() {
        private val _navigateDialog = MutableSharedFlow<Any>(extraBufferCapacity = 1)
        val navigateDialog = _navigateDialog.asSharedFlow()

        val configStateFlow =
            configRepository.configStateFlow.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ConfigState.NoAction,
            )

        fun getStartDestination(): Any {
            return when {
                hasPinCode.invoke() -> {
                    PinCodeLoginNavigation.Root
                }

                hasSeenOnboarding.invoke() -> {
                    PinCodeCreateNavigation.Root
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

        fun checkAppLock() {
            viewModelScope.launch {
                val appLocked = checkAppLock.invoke()
                if (appLocked) {
                    _navigateDialog.tryEmit(PinCodeLoginNavigation.LoginDialog)
                }
            }
        }

        fun saveClosedAppTimestamp() {
            viewModelScope.launch {
                saveClosedAppTimestamp.invoke()
            }
        }
    }
