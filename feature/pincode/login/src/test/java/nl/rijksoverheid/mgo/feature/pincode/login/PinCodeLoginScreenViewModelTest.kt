package nl.rijksoverheid.mgo.feature.pincode.login

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.pincode.TestValidatePinCode
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class PinCodeLoginScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given stored pin is same as input pin, When calling validatePinCode, Then navigate to dashboard`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val validatePinCode = TestValidatePinCode()
            validatePinCode.setStoredPinCode(listOf(1, 2, 3, 4, 5))
            val viewModel = PinCodeLoginScreenViewModel(validatePinCode = validatePinCode)

            viewModel.navigateToDashboard.test {
                // When
                viewModel.validatePinCode(pinCode)

                // Then
                assertEquals(Unit, awaitItem())
            }
        }

    @Test
    fun `Given stored pin is not the same as input pin, When calling validatePinCode, Then update  view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val validatePinCode = TestValidatePinCode()
            validatePinCode.setStoredPinCode(listOf(1, 2, 3, 4, 6))
            val viewModel = PinCodeLoginScreenViewModel(validatePinCode = validatePinCode)

            // When
            viewModel.validatePinCode(pinCode)

            // Then
            viewModel.viewState.test {
                val expectedViewState = PinCodeLoginScreenViewState(subHeading = R.string.pincode_validation_wrong, error = true)
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given stored pin is not the same as input pin, When calling resetError, Then update view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val validatePinCode = TestValidatePinCode()
            validatePinCode.setStoredPinCode(listOf(1, 2, 3, 4, 6))
            val viewModel = PinCodeLoginScreenViewModel(validatePinCode = validatePinCode)

            // When
            viewModel.validatePinCode(pinCode)
            viewModel.resetError()

            // Then
            viewModel.viewState.test {
                val expectedViewState = PinCodeLoginScreenViewState(subHeading = R.string.pincode_validation_wrong, error = false)
                assertEquals(expectedViewState, awaitItem())
            }
        }
}
