package nl.rijksoverheid.mgo.feature.pincode.biometric

import nl.rijksoverheid.mgo.data.pincode.biometric.TestSetLoginWithBiometricEnabled
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class PinCodeBiometricSetupScreenViewModelTest {
    private val setLoginWithBiometricEnabled = TestSetLoginWithBiometricEnabled()
    private val keyValueStore = TestKeyValueStore()

    @Before
    fun setUp() =
        runTest {
            keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, false)
        }

    @Test
    fun `Given viewmodel, When calling setBiometricLoginEnabled, Then use case is called`() {
        // Given
        val viewModel =
            PinCodeBiometricSetupScreenViewModel(
                setLoginWithBiometricEnabled = setLoginWithBiometricEnabled,
                keyValueStore = keyValueStore,
            )

        // When
        viewModel.setBiometricLoginEnabled()

        // Then
        assertTrue(setLoginWithBiometricEnabled.isEnabled())
    }

    @Test
    fun testGetAutomaticLocalisationEnabled() {
        // Given: ViewModel
        val viewModel =
            PinCodeBiometricSetupScreenViewModel(
                setLoginWithBiometricEnabled = setLoginWithBiometricEnabled,
                keyValueStore = keyValueStore,
            )

        // When
        val enabled = viewModel.getAutomaticLocalisationEnabled()

        // Then
        assertEquals(false, enabled)
    }
}
