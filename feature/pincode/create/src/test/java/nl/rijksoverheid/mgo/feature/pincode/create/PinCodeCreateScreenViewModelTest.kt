package nl.rijksoverheid.mgo.feature.pincode.create

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.pincode.strength.TestPinCodeStrengthValidator
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class PinCodeCreateScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  @Test
  fun `Given valid pin code, When calling validatePinCode, Then navigate to confirm`() =
    runTest {
      // Given
      val pinCode = listOf(1, 2, 3, 4, 5)
      val validator = TestPinCodeStrengthValidator(true)
      val viewModel = PinCodeCreateScreenViewModel(validator)

      viewModel.navigateToConfirm.test {
        // When
        viewModel.validatePinCode(pinCode)

        // Then
        assertEquals(pinCode, awaitItem())
      }
    }

  @Test
  fun `Given invalid pin code, When calling validatePinCode, Then update view state`() =
    runTest {
      // Given
      val pinCode = listOf(1, 2, 3, 4, 5)
      val validator = TestPinCodeStrengthValidator(false)
      val viewModel = PinCodeCreateScreenViewModel(validator)

      // When
      viewModel.validatePinCode(pinCode)

      // Then
      viewModel.viewState.test {
        val expectedViewState = PinCodeCreateScreenViewState(error = true)
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun `Given invalid pin code, When calling resetError, Then update view state`() =
    runTest {
      // Given
      val pinCode = listOf(1, 2, 3, 4, 5)
      val validator = TestPinCodeStrengthValidator(false)
      val viewModel = PinCodeCreateScreenViewModel(validator)

      // When
      viewModel.validatePinCode(pinCode)
      viewModel.resetError()

      // Then
      viewModel.viewState.test {
        val expectedViewState = PinCodeCreateScreenViewState(error = false)
        assertEquals(expectedViewState, awaitItem())
      }
    }
}
