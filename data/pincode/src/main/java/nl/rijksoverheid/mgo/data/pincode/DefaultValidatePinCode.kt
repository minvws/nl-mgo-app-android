package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.data.pincode.hash.PinCodeHasher
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Validates a pin code.
 *
 * @param keyValueStore The [KeyValueStore] where the hashed pin code is stored.
 * @param pinCodeHasher The [PinCodeHasher] to validate against a hashed pin code.
 */
internal class DefaultValidatePinCode
  @Inject
  constructor(
    @Named("secureKeyValueStore") private val keyValueStore: KeyValueStore,
    private val pinCodeHasher: PinCodeHasher,
  ) :
  ValidatePinCode {
    /**
     * @param pinCode The pin code to validate.
     * @return True if the pin code is validated.
     */
    override suspend fun invoke(pinCode: List<Int>): Boolean {
      val pinCodeString = pinCode.joinToString(",")
      val storedPinCode = keyValueStore.getString(KEY_PIN_CODE) ?: return false
      return pinCodeHasher.validate(pinCodeString, storedPinCode)
    }
  }
