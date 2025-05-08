package nl.rijksoverheid.mgo.data.pincode.biometric

class TestSetLoginWithBiometricEnabled : SetLoginWithBiometricEnabled {
  private var enabled: Boolean = false

  fun isEnabled(): Boolean {
    return enabled
  }

  override fun invoke() {
    this.enabled = true
  }
}
