package nl.rijksoverheid.mgo.data.pincode.biometric

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.LOGIN_WITH_BIOMETRIC_ENABLED
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultSetLoginWithBiometricEnabled
    @Inject
    constructor(
        private val keyValueStore: KeyValueStore,
    ) : SetLoginWithBiometricEnabled {
        override fun invoke() {
            return runBlocking { keyValueStore.setBoolean(LOGIN_WITH_BIOMETRIC_ENABLED, true) }
        }
    }
