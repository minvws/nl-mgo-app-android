package nl.rijksoverheid.mgo.feature.settings.advanced

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_SKIP_PIN
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [SettingsAdvancedScreen].
 *
 * @param keyValueStore The [KeyValueStore] to get and change the feature toggles for.
 * @param ioDispatcher Dispatcher for IO-bound operations such as file I/O and network requests.
 */
@HiltViewModel
internal class SettingsAdvancedScreenViewModel
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) :
  ViewModel() {
    private val initialViewState =
      SettingsAdvancedScreenViewState(
        automaticLocalisation = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
        flagSecure = keyValueStore.getBoolean(KEY_FLAG_SECURE),
        skipPinCode = keyValueStore.getBoolean(KEY_SKIP_PIN),
      )
    private val _viewState =
      combine(
        keyValueStore.observeBoolean(KEY_AUTOMATIC_LOCALISATION),
        keyValueStore.observeBoolean(KEY_FLAG_SECURE),
        keyValueStore.observeBoolean(KEY_SKIP_PIN),
      ) { automaticLocalisation, flagSecure, skipPin ->
        SettingsAdvancedScreenViewState(automaticLocalisation, flagSecure, skipPin)
      }
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    /**
     * Update feature toggle.
     *
     * @param key The key of the feature toggle.
     * @param enabled True if the feature toggle is enabled.
     */
    fun setToggle(
      key: Preferences.Key<Boolean>,
      enabled: Boolean,
    ) {
      viewModelScope.launch(ioDispatcher) {
        keyValueStore.setBoolean(key, enabled)
      }
    }
  }
