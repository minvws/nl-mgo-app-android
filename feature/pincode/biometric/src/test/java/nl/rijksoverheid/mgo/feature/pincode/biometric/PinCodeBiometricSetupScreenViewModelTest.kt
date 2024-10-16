package nl.rijksoverheid.mgo.feature.pincode.biometric

import nl.rijksoverheid.mgo.data.pincode.biometric.TestSetLoginWithBiometricEnabled
import org.junit.Assert.assertTrue
import org.junit.Test

internal class PinCodeBiometricSetupScreenViewModelTest {
    @Test
    fun `Given viewmodel, When calling setBiometricLoginEnabled, Then use case is called`() {
        // Given
        val setLoginWithBiometricEnabled = TestSetLoginWithBiometricEnabled()
        val viewModel = PinCodeBiometricSetupScreenViewModel(setLoginWithBiometricEnabled)

        // When
        viewModel.setBiometricLoginEnabled()

        // Then
        assertTrue(setLoginWithBiometricEnabled.isEnabled())
    }
}
