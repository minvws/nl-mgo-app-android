package nl.rijksoverheid.mgo.data.pincode.biometric

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

/**
 * Store that the user has enabled biometric login.
 *
 * @param keyValueStore The [KeyValueStore] to store if the user has enabled biometric login.
 */
internal class DefaultSetLoginWithBiometricEnabled
    @Inject
    constructor(
        private val keyValueStore: KeyValueStore,
    ) : SetLoginWithBiometricEnabled {
        override fun invoke() {
            return runBlocking { keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, true) }
        }
    }
