package nl.rijksoverheid.mgo.feature.settings.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class SettingsSecurityScreenViewModel
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : ViewModel() {
        private val _biometricEnabled = keyValueStore.observeBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED)
        val biometricEnabled =
            _biometricEnabled.stateIn(viewModelScope, SharingStarted.Lazily, keyValueStore.getBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED))

        fun setBiometricEnabled(enabled: Boolean) {
            viewModelScope.launch {
                keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, enabled)
            }
        }
    }
