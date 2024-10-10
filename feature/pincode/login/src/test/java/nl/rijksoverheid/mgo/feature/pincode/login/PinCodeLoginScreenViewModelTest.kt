package nl.rijksoverheid.mgo.feature.pincode.login

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.pincode.TestValidatePinCode
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class PinCodeLoginScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given pin code exists, When calling resetPinCode, Then reset the view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val validatePinCode = TestValidatePinCode()
            val viewModel = PinCodeLoginScreenViewModel(validatePinCode)

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
    fun `Given stored pin is same as input pin, When calling addPinCode, Then navigate to dashboard`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4)
            val validatePinCode = TestValidatePinCode()
            validatePinCode.setStoredPinCode(listOf(1, 2, 3, 4, 5))
            val viewModel = PinCodeLoginScreenViewModel(validatePinCode = validatePinCode)

            // When
            viewModel.setPinCode(pinCode)
            viewModel.navigateToDashboard.test {
                viewModel.addPinCodeNumber(5)

                // Then
                assertEquals(Unit, awaitItem())
            }
        }

    @Test
    fun `Given stored pin is not the same as input pin, When calling addPinCode, Then show error`() =
        runTest {
            // Given
            val validatePinCode = TestValidatePinCode()
            validatePinCode.setStoredPinCode(listOf(1, 2, 3, 4, 5))
            val viewModel = PinCodeLoginScreenViewModel(validatePinCode = validatePinCode)

            // When
            viewModel.setPinCode(listOf(1, 2, 3, 4))
            viewModel.addPinCodeNumber(6)

            // Then
            viewModel.viewState.test {
                val expectedViewState =
                    PinCodeLoginScreenViewState(
                        pinCode = listOf(1, 2, 3, 4, 6),
                        subHeading = CopyR.string.pincode_validation_wrong,
                        error = true,
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }
}
