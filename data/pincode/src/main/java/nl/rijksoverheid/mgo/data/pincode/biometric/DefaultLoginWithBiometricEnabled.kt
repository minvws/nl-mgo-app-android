package nl.rijksoverheid.mgo.data.pincode.biometric

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.LOGIN_WITH_BIOMETRIC_ENABLED
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultLoginWithBiometricEnabled
    @Inject
    constructor(
        private val keyValueStore: KeyValueStore,
    ) : LoginWithBiometricEnabled {
        override fun invoke(): Boolean {
            return runBlocking { keyValueStore.getBoolean(LOGIN_WITH_BIOMETRIC_ENABLED) }
        }
    }
