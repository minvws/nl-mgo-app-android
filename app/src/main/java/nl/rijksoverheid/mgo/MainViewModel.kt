package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
import nl.rijksoverheid.mgo.data.digid.IsDigidAuthenticated
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_THEME
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.lifecycle.AppLifecycleRepository
import nl.rijksoverheid.mgo.lock.AppLocked
import nl.rijksoverheid.mgo.lock.SaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.digid.DigidNavigation
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeLoginNavigation
import javax.inject.Inject
import javax.inject.Named

/**
 * Viewmodel that is attached to the only activity in the app. Handles functionality that is related to navigation when
 * launching the app.
 * @param showDeviceRootedDialog Use case that checks if the device has been rooted.
 * @param appLocked Use case that checks if the app should be locked.
 * @param saveClosedAppTimestamp Use case that saves locally saves a timestamp. The timestamp represents the last time the app was closed.
 * @param hasPinCode Use case that checks if the user has a pin code set.
 * @param hasSeenOnboarding Use case that checks if the user has finished the onboarding.
 * @param featureToggleRepository Repository that handles feature toggle actions.
 * @param keyValueStore Store to save a key value pair into.
 * @param isDigidAuthenticated Use case to check if the user has authenticated with DigiD.
 * @param appLifecycleRepository Repository that can observe the app life cycle state.
 */
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
    val appLifecycleRepository: AppLifecycleRepository,
  ) : ViewModel() {
    private val _flagSecureFeatureToggle = MutableSharedFlow<Boolean>(replay = 1, extraBufferCapacity = 1)
    val flagSecureFeatureToggle = _flagSecureFeatureToggle.asSharedFlow()

    private val _navigateDialog = MutableSharedFlow<Any>(extraBufferCapacity = 1)
    val navigateDialog = _navigateDialog.asSharedFlow()

    private val _appTheme = MutableStateFlow(getAppTheme(keyValueStore.getString(KEY_APP_THEME)))
    val appTheme = _appTheme.asStateFlow()

    init {
      viewModelScope.launch {
        // Handle if the flag secure (allow screenshots) feature toggle is enabled
        launch {
          featureToggleRepository.observe(FeatureToggleId.FlagSecure).collectLatest { enabled ->
            _flagSecureFeatureToggle.tryEmit(enabled)
          }
        }

        // Handle app theming
        launch {
          keyValueStore.observeString(KEY_APP_THEME).collectLatest { appThemeString ->
            _appTheme.emit(getAppTheme(appThemeString))
          }
        }
      }
    }

    /**
     * Get the first navigation destination to show when launching the app.
     */
    fun getStartDestination(): Any =
      when {
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

    /**
     * Check if the app needs to be locked.
     */
    fun checkAppLock() {
      viewModelScope.launch {
        val appLocked = appLocked.invoke()
        if (appLocked) {
          lockApp()
        }
      }
    }

    fun lockApp() {
      _navigateDialog.tryEmit(PinCodeLoginNavigation.LoginDialog)
    }

    /**
     * Save the timestamp locally so we know if the app needs to be locked when coming back from the background.
     */
    fun saveClosedAppTimestamp() {
      viewModelScope.launch {
        saveClosedAppTimestamp.invoke()
      }
    }

    /**
     * @return True if the automatic localisation needs to be shown instead of the manual one.
     */
    fun getAutomaticLocalisationEnabled(): Boolean = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION)
  }
