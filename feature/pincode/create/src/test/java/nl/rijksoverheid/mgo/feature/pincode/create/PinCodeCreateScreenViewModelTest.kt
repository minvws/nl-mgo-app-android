package nl.rijksoverheid.mgo.feature.pincode.create

import app.cash.turbine.test
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class PinCodeCreateScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given pin code exists, When calling resetPinCode, Then reset the view state`() =
        runTest {
            // Given
            val pinCode = listOf(1, 2, 3, 4, 5)
            val viewModel = PinCodeCreateScreenViewModel()

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
    fun `Given no pin code, When calling addPinCodeNumber, Then change the pin code`() =
        runTest {
            // Given
            val viewModel = PinCodeCreateScreenViewModel()

            // When
            viewModel.addPinCodeNumber(1)
            viewModel.addPinCodeNumber(2)
            viewModel.addPinCodeNumber(3)

            // Then
            viewModel.viewState.test {
                assertEquals(listOf(1, 2, 3), awaitItem().pinCode)
            }
        }

    @Test
    fun `Given 4 pin code numbers, When calling addPinCodeNumber, Then navigate to confirm screen`() =
        runTest {
            // Given
            val viewModel = PinCodeCreateScreenViewModel()
            viewModel.setPinCode(listOf(1, 2, 3, 4))

            // When
            viewModel.navigateToConfirm.test {
                viewModel.addPinCodeNumber(5)

                // Then
                assertEquals(listOf(1, 2, 3, 4, 5), awaitItem())
            }
        }
}
