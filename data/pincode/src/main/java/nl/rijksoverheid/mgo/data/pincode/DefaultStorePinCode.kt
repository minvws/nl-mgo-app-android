package nl.rijksoverheid.mgo.data.pincode

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.pincode.hash.PinCodeHasher
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject

/**
 * Stores a hashed pin code.
 *
 * @param secureKeyValueStore [KeyValueStore] to save the hashed pin code into.
 * @param pinCodeHasher [PinCodeHasher] to hash the pin code.
 */
internal class DefaultStorePinCode
  @Inject
  constructor(private val secureKeyValueStore: KeyValueStore, private val pinCodeHasher: PinCodeHasher) : StorePinCode {
    /**
     * @param pinCode The pin code to store.
     */
    override operator fun invoke(pinCode: List<Int>) {
      val pinCodeString = pinCode.joinToString(",")
      val hashedPinCode = pinCodeHasher.hash(pinCodeString)
      runBlocking { secureKeyValueStore.setString(KEY_PIN_CODE, hashedPinCode) }
    }
  }
