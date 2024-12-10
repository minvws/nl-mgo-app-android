package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.digid.IsDigidAuthenticated
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.lock.AppLocked
import nl.rijksoverheid.mgo.lock.SaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.digid.DigidNavigation
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeLoginNavigation
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
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
        @Named("keyValueStore") val keyValueStore: KeyValueStore,
        val isDigidAuthenticated: IsDigidAuthenticated,
    ) : ViewModel() {
        private val _flagSecureFeatureToggle = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
        val flagSecureFeatureToggle = _flagSecureFeatureToggle.asSharedFlow()

        private val _navigateDialog = MutableSharedFlow<Any>(extraBufferCapacity = 1)
        val navigateDialog = _navigateDialog.asSharedFlow()

        init {
            viewModelScope.launch {
                featureToggleRepository.observe(FeatureToggleId.FlagSecure).collectLatest { enabled ->
                    _flagSecureFeatureToggle.tryEmit(enabled)
                }
            }
        }

        fun getStartDestination(): Any {
            return when {
                // If the user has not seen the onboarding, show the onboarding flow.
                !hasSeenOnboarding.invoke() -> {
                    OnboardingNavigation.Root
                }

                // If the user has not create a pin code, show the create pin code flow.
                !hasPinCode.invoke() -> {
                    PinCodeCreateNavigation.Root
                }

                // If the user has not yet authenticated with DigiD, show the DigiD flow.
                !isDigidAuthenticated.invoke() -> {
                    DigidNavigation.Root
                }

                // If all above things are not true, then we can show the dashboard.
                else -> {
                    if (featureToggleRepository.get(FeatureToggleId.SkipPin)) {
                        DashboardNavigation.Root
                    } else {
                        // Lock dashboard with pin code first.
                        PinCodeLoginNavigation.Root
                    }
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

        fun getAutomaticLocalisationEnabled(): Boolean {
            return keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION)
        }
    }
