package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.lock.AppLocked
import nl.rijksoverheid.mgo.lock.SaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeLoginNavigation
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainViewModel
    @Inject
    constructor(
        val showDeviceRootedDialog: ShowDeviceRootedDialog,
        private val appLocked: AppLocked,
        private val saveClosedAppTimestamp: SaveClosedAppTimestamp,
        private val hasPinCode: HasPinCode,
        private val hasSeenOnboarding: HasSeenOnboarding,
        private val featureToggleRepository: FeatureToggleRepository,
    ) : ViewModel() {
        private val _navigateDialog = MutableSharedFlow<Any>(extraBufferCapacity = 1)
        val navigateDialog = _navigateDialog.asSharedFlow()

        fun getStartDestination(): Any {
            return when {
                hasPinCode.invoke() -> {
                    if (featureToggleRepository.get(FeatureToggleId.SkipPin)) {
                        DashboardNavigation.Root
                    } else {
                        PinCodeLoginNavigation.Root
                    }
                }

                hasSeenOnboarding.invoke() -> {
                    PinCodeCreateNavigation.Root
                }

                else -> {
                    OnboardingNavigation.Root
                }
            }
        }

        fun checkAppLock() {
            viewModelScope.launch {
                val appLocked = appLocked.invoke()
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
