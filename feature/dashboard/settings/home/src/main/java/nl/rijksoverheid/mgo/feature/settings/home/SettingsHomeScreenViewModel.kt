package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
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
 */
@HiltViewModel
internal class SettingsHomeScreenViewModel
    @Inject
    constructor(
        @Named("keyValueStore") keyValueStore: KeyValueStore,
    ) : ViewModel() {
        private val _appTheme = keyValueStore.observeString(KEY_APP_THEME).map { appThemeString -> getAppTheme(appThemeString) }
        val appTheme = _appTheme.stateIn(viewModelScope, SharingStarted.Lazily, getAppTheme(keyValueStore.getString(KEY_APP_THEME)))
    }
