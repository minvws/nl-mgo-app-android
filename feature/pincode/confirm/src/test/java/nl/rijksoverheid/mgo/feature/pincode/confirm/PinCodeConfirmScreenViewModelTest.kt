package nl.rijksoverheid.mgo.feature.pincode.confirm

import app.cash.turbine.test
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
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
            val viewModel = PinCodeConfirmScreenViewModel(pinCodeToMatch = listOf())

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
    fun `Given pin code matches with pin code to compare with, When calling addPinCodeNumber, Then navigate to dashboard`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val viewModel = PinCodeConfirmScreenViewModel(pinCodeToMatch = pinCode)
            viewModel.setPinCode(listOf(1, 2, 3, 4))

            // When
            viewModel.navigateToDashboard.test {
                viewModel.addPinCodeNumber(5)

                // Then
                assertEquals(Unit, awaitItem())
            }
        }

    @Test
    fun `Given pin code does not match with pin code to compare with, When calling addPinCodeNumber, Then navigate to dashboard`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val viewModel = PinCodeConfirmScreenViewModel(pinCodeToMatch = pinCode)
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
}
