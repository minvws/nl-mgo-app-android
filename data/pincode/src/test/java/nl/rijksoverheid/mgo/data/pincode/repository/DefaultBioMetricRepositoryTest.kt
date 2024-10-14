package nl.rijksoverheid.mgo.data.pincode.repository

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import io.mockk.every
import io.mockk.mockk
import nl.rijksoverheid.mgo.data.pincode.biometric.DefaultBioMetricRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultBioMetricRepositoryTest {
    @Test
    fun `Given device has biometric support, When calling deviceHasSupport, Then return true`() {
        // Given
        val bioMetricManager = mockk<BiometricManager>()
        every { bioMetricManager.canAuthenticate(BIOMETRIC_STRONG) } answers { BiometricManager.BIOMETRIC_SUCCESS }
        val repository = DefaultBioMetricRepository(bioMetricManager)

        // When
        val deviceHasSupport = repository.deviceHasSupport()

        // Then
        assertTrue(deviceHasSupport)
    }

    @Test
    fun `Given device has no biometric support, When calling deviceHasSupport, Then return false`() {
        // Given
        val bioMetricManager = mockk<BiometricManager>()
        every { bioMetricManager.canAuthenticate(BIOMETRIC_STRONG) } answers { BiometricManager.BIOMETRIC_STATUS_UNKNOWN }
        val repository = DefaultBioMetricRepository(bioMetricManager)

        // When
        val deviceHasSupport = repository.deviceHasSupport()

        // Then
        assertFalse(deviceHasSupport)
    }
}
