package nl.rijksoverheid.mgo.data.pincode

class TestValidatePinCode : ValidatePinCode {
  private var storedPinCode: List<Int> = listOf()

  fun setStoredPinCode(pinCode: List<Int>) {
    this.storedPinCode = pinCode
  }

  override suspend fun invoke(pinCode: List<Int>): Boolean {
    return storedPinCode == pinCode
  }
}
