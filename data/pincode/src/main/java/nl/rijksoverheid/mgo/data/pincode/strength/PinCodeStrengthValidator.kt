package nl.rijksoverheid.mgo.data.pincode.strength

/**
 * Validates the strength of a pin code.
 */
interface PinCodeStrengthValidator {
  /**
   * @param pinCode The pin code to validate.
   * @return True if pin code is validated.
   */
  operator fun invoke(pinCode: List<Int>): Boolean
}
