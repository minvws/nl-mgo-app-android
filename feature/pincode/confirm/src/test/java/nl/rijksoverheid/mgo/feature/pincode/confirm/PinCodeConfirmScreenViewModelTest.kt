package nl.rijksoverheid.mgo.feature.pincode.confirm

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.pincode.TestStorePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.TestDeviceHasBiometric
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class PinCodeConfirmScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given pin code exists, When calling resetPinCode, Then reset the view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = listOf(),
                    deviceHasBiometric = deviceHasBiometric,
                )

            // When
            viewModel.setPinCode(pinCode)

            viewModel.viewState.test {
                viewModel.resetPinCode()

                // Then
                assertEquals(pinCode, awaitItem().pinCode)
                assertEquals(listOf<Int>(), awaitItem().pinCode)
            }
        }

    @Test
    fun `Given pin code matches and phone has biometric support, When calling addPinCodeNumber, Then navigate to biometric`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(true)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = pinCode,
                    deviceHasBiometric = deviceHasBiometric,
                )
            viewModel.setPinCode(listOf(1, 2, 3, 4))

            // When
            viewModel.navigate.test {
                viewModel.addPinCodeNumber(5)

                // Then
                assertEquals(PinCodeConfirmScreenNextNavigation.BIOMETRIC, awaitItem())
            }
        }

    @Test
    fun `Given pin code matches and phone has no biometric support, When calling addPinCodeNumber, Then navigate to biometric`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = pinCode,
                    deviceHasBiometric = deviceHasBiometric,
                )
            viewModel.setPinCode(listOf(1, 2, 3, 4))

            // When
            viewModel.navigate.test {
                viewModel.addPinCodeNumber(5)

                // Then
                assertEquals(PinCodeConfirmScreenNextNavigation.DASHBOARD, awaitItem())
            }
        }

    @Test
    fun `Given pin code does not match with pin code to compare with, When calling addPinCodeNumber, Then update view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = pinCode,
                    deviceHasBiometric = deviceHasBiometric,
                )
            viewModel.setPinCode(listOf(1, 2, 3, 4))

            // When
            viewModel.addPinCodeNumber(6)

            // Then
            viewModel.viewState.test {
                val expectedViewState =
                    PinCodeConfirmScreenViewState(
                        pinCode = listOf(1, 2, 3, 4, 6),
                        subHeading = CopyR.string.pincode_confirm_mismatch,
                        error = true,
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given pin code should be stored, When calling addPinCodeNumber, Then store the pincode`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val storePinCode = TestStorePinCode()
            val deviceHasBiometric = TestDeviceHasBiometric(false)
            val biometricRepository = TestDeviceHasBiometric(deviceHasSupport = false)
            val viewModel =
                PinCodeConfirmScreenViewModel(
                    storePinCode = storePinCode,
                    pinCodeToMatch = pinCode,
                    deviceHasBiometric = deviceHasBiometric,
                )

            // When
            viewModel.setPinCode(listOf(1, 2, 3, 4))
            viewModel.addPinCodeNumber(5)

            // Then
            assertTrue(storePinCode.assertStoredPinCode(pinCode))
        }
}
