package nl.rijksoverheid.mgo.data.pincode

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Check if a pin code is stored.
 *
 * @param keyValueStore The [KeyValueStore] where the pin code is stored.
 */
internal class DefaultHasPinCode
  @Inject
  constructor(
    @Named("secureKeyValueStore") private val keyValueStore: KeyValueStore,
  ) : HasPinCode {
    /**
     * @return True if a pin code is stored.
     */
    override fun invoke(): Boolean {
      return runBlocking { keyValueStore.getString(KEY_PIN_CODE) } != null
    }
  }
