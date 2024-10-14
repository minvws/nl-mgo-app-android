package nl.rijksoverheid.mgo.data.pincode.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG

internal class DefaultBioMetricRepository(
    private val biometricManager: BiometricManager,
) : BioMetricRepository {
    override fun deviceHasSupport(): Boolean {
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }
}
