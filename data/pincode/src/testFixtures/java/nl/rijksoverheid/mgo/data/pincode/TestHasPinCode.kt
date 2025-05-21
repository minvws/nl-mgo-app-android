package nl.rijksoverheid.mgo.data.pincode

class TestHasPinCode : HasPinCode {
  private var has: Boolean = false

  fun set(has: Boolean) {
    this.has = has
  }

  override fun invoke(): Boolean {
    return has
  }
}
