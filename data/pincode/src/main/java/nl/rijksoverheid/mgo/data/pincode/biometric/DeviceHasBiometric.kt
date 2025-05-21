package nl.rijksoverheid.mgo.data.pincode.biometric

/**
 * Check if this device supports biometric login (https://source.android.com/docs/security/features/biometric).
 */
interface DeviceHasBiometric {
  /**
   * @return True if this device supports biometric login.
   */
  operator fun invoke(): Boolean
}
