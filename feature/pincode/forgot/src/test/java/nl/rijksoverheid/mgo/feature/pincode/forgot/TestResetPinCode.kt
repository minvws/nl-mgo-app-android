package nl.rijksoverheid.mgo.feature.pincode.forgot

import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.ResetPinCode

class TestResetPinCode : ResetPinCode {
  private var pinCode: MutableList<Int> = mutableListOf(1, 2, 3)

  fun getPinCode(): List<Int> {
    return pinCode
  }

  override suspend fun invoke() {
    pinCode.clear()
  }
}
