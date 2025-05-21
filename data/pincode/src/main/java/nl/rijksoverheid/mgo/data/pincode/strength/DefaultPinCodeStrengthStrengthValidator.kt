package nl.rijksoverheid.mgo.data.pincode.strength

/**
 * Validates the strength of a pin code.
 */
internal class DefaultPinCodeStrengthStrengthValidator : PinCodeStrengthValidator {
  /**
   * @param pinCode The pin code to validate.
   * @return True if pin code is validated.
   */
  override fun invoke(pinCode: List<Int>): Boolean {
    val pinCodeString = pinCode.joinToString("")

    // Pin code is not valid if it is a frequently used pin code
    val frequentlyUsed =
      listOf(
        "12345",
        "54321",
        "13579",
        "12321",
        "90210",
        "38317",
        "09876",
        "98765",
        "01234",
        "42069",
        "00012",
        "00098",
        "11123",
        "21012",
        "31415",
        "32123",
        "36963",
        "43210",
        "80087",
        "99987",
      )
    if (frequentlyUsed.contains(pinCodeString)) {
      return false
    }

    // Pin code is not valid if there are less than three unique values
    if (pinCode.toSet().size <= 3) {
      return false
    }

    return true
  }
}
