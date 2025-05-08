package nl.rijksoverheid.mgo.data.pincode

class TestStorePinCode : StorePinCode {
  private var pinCode: List<Int> = listOf()

  fun assertStoredPinCode(pinCode: List<Int>): Boolean {
    return this.pinCode == pinCode
  }

  override fun invoke(pinCode: List<Int>) {
    this.pinCode = pinCode
  }
}
