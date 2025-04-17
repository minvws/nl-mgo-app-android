package nl.rijksoverheid.mgo.feature.settings.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [SettingsSecurityScreen].
 *
 * @param keyValueStore The [KeyValueStore] to get and set if biometric login is enabled.
 * @param ioDispatcher Dispatcher for IO-bound operations such as file I/O and network requests.
 */
@HiltViewModel
internal class SettingsSecurityScreenViewModel
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    private val _biometricEnabled = keyValueStore.observeBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED)
    val biometricEnabled =
      _biometricEnabled.stateIn(viewModelScope, SharingStarted.Lazily, keyValueStore.getBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED))

    /**
     * Set if biometric login for the app is enabled or not.
     *
     * @param enabled True if biometric login is enabled.
     */
    fun setBiometricEnabled(enabled: Boolean) {
      viewModelScope.launch(ioDispatcher) {
        keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, enabled)
      }
    }
  }
