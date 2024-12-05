package nl.rijksoverheid.mgo.feature.pincode.confirm

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.pincode.TestStorePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.TestDeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class PinCodeConfirmScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val keyValueStore = TestKeyValueStore()

    @Before
    fun setUp() =
        runTest {
            keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, false)
        }

    @Test
    fun `Given pin code matches and phone has biometric support, When calling validatePinCode, Then navigate to biometric`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(true)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = listOf(1, 2, 3, 4, 5),
                    deviceHasBiometric = deviceHasBiometric,
                    keyValueStore = keyValueStore,
                )

            viewModel.navigate.test {
                // When
                viewModel.validatePinCode(pinCode)

                // Then
                assertEquals(PinCodeConfirmScreenNextNavigation.BIOMETRIC, awaitItem())
            }
        }

    @Test
    fun `Given pin code matches and phone does not have biometric support, When calling validatePinCode, Then navigate to biometric`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = listOf(1, 2, 3, 4, 5),
                    deviceHasBiometric = deviceHasBiometric,
                    keyValueStore = keyValueStore,
                )

            viewModel.navigate.test {
                // When
                viewModel.validatePinCode(pinCode)

                // Then
                assertEquals(PinCodeConfirmScreenNextNavigation.DASHBOARD, awaitItem())
            }
        }

    @Test
    fun testValidatePinCodeNavigateToAutomaticLocalisation() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = listOf(1, 2, 3, 4, 5),
                    deviceHasBiometric = deviceHasBiometric,
                    keyValueStore = keyValueStore,
                )

            // Given: automatic localisation feature flag is enabled
            keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, true)

            viewModel.navigate.test {
                // When
                viewModel.validatePinCode(pinCode)

                // Then
                assertEquals(PinCodeConfirmScreenNextNavigation.AUTOMATIC_LOCALISATION, awaitItem())
            }
        }

    @Test
    fun `Given pin code does not match, When calling resetError, Then update view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = listOf(1, 2, 3, 4, 6),
                    deviceHasBiometric = deviceHasBiometric,
                    keyValueStore = keyValueStore,
                )

            // When
            viewModel.validatePinCode(pinCode)
            viewModel.resetError()

            // Then
            viewModel.viewState.test {
                val expectedViewState = PinCodeConfirmScreenViewState(error = false)
                assertEquals(expectedViewState, awaitItem())
            }
        }
}
