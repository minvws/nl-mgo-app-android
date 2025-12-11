package nl.rijksoverheid.mgo.feature.settings.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.KEY_APP_THEME
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsDisplayScreenViewModel
  @Inject
  constructor(
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
  ) : ViewModel() {
    private val _appTheme = keyValueStorage.observe<String>(KEY_APP_THEME).map { appThemeString -> getAppTheme(appThemeString) }
    val appTheme = _appTheme.stateIn(viewModelScope, SharingStarted.Lazily, getAppTheme(keyValueStorage.get(KEY_APP_THEME)))

    fun setTheme(theme: AppTheme) {
      viewModelScope.launch(ioDispatcher) {
        keyValueStorage.save(KEY_APP_THEME, theme.toString())
      }
    }
  }
