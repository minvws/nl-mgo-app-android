package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_THEME
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [SettingsHomeScreen].
 *
 * @param keyValueStore The [KeyValueStore] to get and set the [AppTheme].
 * @param deviceHasBiometric The [DeviceHasBiometric] to check if the device has biometric capabilities.
 */
@HiltViewModel
internal class SettingsHomeScreenViewModel
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
    @Named("isDebug") isDebug: Boolean,
    private val cacheFileStore: CacheFileStore,
    private val organizationRepository: OrganizationRepository,
    deviceHasBiometric: DeviceHasBiometric,
  ) : ViewModel() {
    private val _navigateToOnboarding = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToOnboarding = _navigateToOnboarding.asSharedFlow()

    private val initialViewState =
      SettingsHomeScreenViewState(
        appTheme = getAppTheme(keyValueStore.getString(KEY_APP_THEME)),
        deviceHasBiometric = deviceHasBiometric.invoke(),
        isDebug = isDebug,
      )
    private val _viewState =
      keyValueStore.observeString(KEY_APP_THEME)
        .map { appThemeString -> getAppTheme(appThemeString) }
        .map { appTheme ->
          SettingsHomeScreenViewState(appTheme = appTheme, isDebug = isDebug, deviceHasBiometric = deviceHasBiometric.invoke())
        }

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    fun resetApp() {
      viewModelScope.launch {
        keyValueStore.clear()
        secureKeyValueStore.clear()
        cacheFileStore.deleteAll()
        organizationRepository.deleteAll()
        _navigateToOnboarding.tryEmit(Unit)
      }
    }
  }
