package nl.rijksoverheid.mgo.feature.settings.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_THEME
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [SettingsDisplayScreen].
 *
 * @param keyValueStore The [KeyValueStore] to get and set the [AppTheme].
 */
@HiltViewModel
internal class SettingsDisplayScreenViewModel
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) : ViewModel() {
    private val _appTheme = keyValueStore.observeString(KEY_APP_THEME).map { appThemeString -> getAppTheme(appThemeString) }
    val appTheme = _appTheme.stateIn(viewModelScope, SharingStarted.Lazily, getAppTheme(keyValueStore.getString(KEY_APP_THEME)))

    /**
     * Set the selected app theme.
     *
     * @param theme The selected [AppTheme].
     */
    fun setTheme(theme: AppTheme) {
      viewModelScope.launch {
        keyValueStore.setString(KEY_APP_THEME, theme.toString())
      }
    }
  }
