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
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsAdvancedScreenViewModel
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    private val initialViewState =
      SettingsAdvancedScreenViewState(
        automaticLocalisation = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
        flagSecure = keyValueStore.getBoolean(KEY_FLAG_SECURE),
      )
    private val _viewState =
      combine(
        keyValueStore.observeBoolean(KEY_AUTOMATIC_LOCALISATION),
        keyValueStore.observeBoolean(KEY_FLAG_SECURE),
      ) { automaticLocalisation, flagSecure ->
        SettingsAdvancedScreenViewState(automaticLocalisation, flagSecure)
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
