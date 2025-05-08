package nl.rijksoverheid.mgo.data.pincode.biometric

/**
 * Check if the user has enabled biometric login.
 */
interface LoginWithBiometricEnabled {
  /**
   * @return True if the user has enabled biometric login.
   */
  operator fun invoke(): Boolean
}
