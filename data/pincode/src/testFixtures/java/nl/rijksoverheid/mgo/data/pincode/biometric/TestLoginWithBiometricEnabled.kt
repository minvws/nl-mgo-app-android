package nl.rijksoverheid.mgo.data.pincode.biometric

class TestLoginWithBiometricEnabled(private val enabled: Boolean) : LoginWithBiometricEnabled {
  override fun invoke(): Boolean {
    return enabled
  }
}
