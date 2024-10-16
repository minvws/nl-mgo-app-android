package nl.rijksoverheid.mgo.data.pincode.biometric

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultSetLoginWithBiometricEnabled
    @Inject
    constructor(
        private val keyValueStore: KeyValueStore,
    ) : SetLoginWithBiometricEnabled {
        override fun invoke() {
            return runBlocking { keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, true) }
        }
    }
