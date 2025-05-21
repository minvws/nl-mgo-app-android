package nl.rijksoverheid.mgo.feature.pincode.forgot

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class PinCodeForgotScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  @Test
  fun `Given pin code, When calling createAccount, Then clear pin code and navigate to pin code create`() =
    runTest {
      // Given
      val resetPinCode = TestResetPinCode()
      val viewModel = PinCodeForgotScreenViewModel(resetPinCode)

      viewModel.navigateToPinCodeCreate.test {
        // When
        viewModel.createNewAccount()

        // Then
        assertEquals(0, resetPinCode.getPinCode().size)
        assertEquals(Unit, awaitItem())
      }
    }
}
