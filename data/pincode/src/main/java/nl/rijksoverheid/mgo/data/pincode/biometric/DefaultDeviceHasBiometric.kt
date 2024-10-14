package nl.rijksoverheid.mgo.data.pincode.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG

internal class DefaultDeviceHasBiometric(private val biometricManager: BiometricManager) : DeviceHasBiometric {
    override fun invoke(): Boolean {
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }
}
