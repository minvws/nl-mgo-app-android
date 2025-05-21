package nl.rijksoverheid.mgo.data.pincode.hash

class TestPinCodeHasher : PinCodeHasher {
  override fun hash(pinCode: String): String {
    return pinCode.reversed()
  }

  override fun validate(
    pinCode: String,
    hash: String,
  ): Boolean {
    return hash == pinCode
  }
}
