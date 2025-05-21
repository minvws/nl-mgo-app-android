package nl.rijksoverheid.mgo.data.pincode

/**
 * Validates a pin code.
 */
interface ValidatePinCode {
  /**
   * @param pinCode The pin code to validate.
   * @return True if the pin code is validated.
   */
  suspend operator fun invoke(pinCode: List<Int>): Boolean
}
