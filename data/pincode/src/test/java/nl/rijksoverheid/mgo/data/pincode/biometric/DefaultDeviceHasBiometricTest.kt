package nl.rijksoverheid.mgo.data.pincode.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultDeviceHasBiometricTest {
  @Test
  fun `Given device has biometric support, When calling use case, Then return true`() {
    // Given
    val bioMetricManager = mockk<BiometricManager>()
    every { bioMetricManager.canAuthenticate(BIOMETRIC_STRONG) } answers { BiometricManager.BIOMETRIC_SUCCESS }
    val deviceHasBiometric = DefaultDeviceHasBiometric(bioMetricManager)

    // When
    val deviceHasSupport = deviceHasBiometric.invoke()

    // Then
    assertTrue(deviceHasSupport)
  }

  @Test
  fun `Given device has no biometric support, When calling deviceHasSupport, Then return false`() {
    // Given
    val bioMetricManager = mockk<BiometricManager>()
    every { bioMetricManager.canAuthenticate(BIOMETRIC_STRONG) } answers { BiometricManager.BIOMETRIC_STATUS_UNKNOWN }
    val deviceHasBiometric = DefaultDeviceHasBiometric(bioMetricManager)

    // When
    val deviceHasSupport = deviceHasBiometric.invoke()

    // Then
    assertFalse(deviceHasSupport)
  }
}
