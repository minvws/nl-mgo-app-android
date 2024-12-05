package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.biometric.SetLoginWithBiometricEnabled
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class PinCodeBiometricSetupScreenViewModel
    @Inject
    constructor(
        private val setLoginWithBiometricEnabled: SetLoginWithBiometricEnabled,
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : ViewModel() {
        fun setBiometricLoginEnabled() {
            setLoginWithBiometricEnabled.invoke()
        }

        fun getAutomaticLocalisationEnabled(): Boolean {
            return keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION)
        }
    }
