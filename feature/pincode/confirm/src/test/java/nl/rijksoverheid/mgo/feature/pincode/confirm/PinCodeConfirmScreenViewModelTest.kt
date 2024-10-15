package nl.rijksoverheid.mgo.feature.pincode.confirm

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.pincode.TestStorePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.TestDeviceHasBiometric
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class PinCodeConfirmScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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
                )

            viewModel.navigate.test {
                // When
                viewModel.validatePinCode(pinCode)

                // Then
                assertEquals(PinCodeConfirmScreenNextNavigation.DASHBOARD, awaitItem())
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
                )

            // When
            viewModel.validatePinCode(pinCode)
            viewModel.resetError()

            // Then
            viewModel.viewState.test {
                val expectedViewState = PinCodeConfirmScreenViewState(subHeading = R.string.pincode_confirm_mismatch, error = false)
                assertEquals(expectedViewState, awaitItem())
            }
        }
}
