package nl.rijksoverheid.mgo.data.pincode.hash

/**
 * Hashing algorithm for a pin code.
 */
interface PinCodeHasher {
  /**
   * Hash a pin code.
   * @param pinCode The pin code to hash.
   * @return The hashed pin code.
   */
  fun hash(pinCode: String): String

  /**
   * Validates a hashed pin code.
   * @param pinCode The pin code to validate.
   * @param hash The hashed pin code to check against.
   * @return True if the pin codes match.
   */
  fun validate(
    pinCode: String,
    hash: String,
  ): Boolean
}
