package nl.rijksoverheid.mgo.data.pincode.biometric

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject

/**
 * Check if the user has enabled biometric login.
 *
 * @param keyValueStore The [KeyValueStore] to get from if biometric is enabled.
 */
internal class DefaultLoginWithBiometricEnabled
  @Inject
  constructor(
    private val keyValueStore: KeyValueStore,
  ) : LoginWithBiometricEnabled {
    /**
     * @return True if the user has enabled biometric login.
     */
    override fun invoke(): Boolean {
      return runBlocking { keyValueStore.getBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED) }
    }
  }
