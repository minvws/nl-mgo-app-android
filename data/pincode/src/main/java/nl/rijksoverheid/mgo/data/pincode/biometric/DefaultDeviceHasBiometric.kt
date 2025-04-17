package nl.rijksoverheid.mgo.data.pincode.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG

/**
 * Check if this device supports biometric login (https://source.android.com/docs/security/features/biometric).
 *
 * @param biometricManager The [BiometricManager].
 */
internal class DefaultDeviceHasBiometric(private val biometricManager: BiometricManager) : DeviceHasBiometric {
  /**
   * @return True if this device supports biometric login.
   */
  override fun invoke(): Boolean {
    return biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
  }
}
