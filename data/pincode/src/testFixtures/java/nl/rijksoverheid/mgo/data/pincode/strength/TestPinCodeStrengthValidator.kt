package nl.rijksoverheid.mgo.data.pincode.strength

class TestPinCodeStrengthValidator(private val valid: Boolean) : PinCodeStrengthValidator {
  override fun invoke(pinCode: List<Int>): Boolean {
    return valid
  }
}
