package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_THEME
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

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
        @Named("keyValueStore") keyValueStore: KeyValueStore,
        deviceHasBiometric: DeviceHasBiometric,
    ) : ViewModel() {
        private val initialViewState =
            SettingsHomeScreenViewState(
                appTheme = getAppTheme(keyValueStore.getString(KEY_APP_THEME)),
                deviceHasBiometric = deviceHasBiometric.invoke(),
            )
        private val _viewState =
            keyValueStore.observeString(KEY_APP_THEME)
                .map { appThemeString -> getAppTheme(appThemeString) }
                .map { appTheme ->
                    SettingsHomeScreenViewState(appTheme = appTheme, deviceHasBiometric = deviceHasBiometric.invoke())
                }

        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
    }
