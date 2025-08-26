package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import nl.rijksoverheid.mgo.rules.SetupAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after going through the pin code forgot flow, the pin code create screen is showing.
 */
class PinCodeForgotTest {
  @get:Rule
  val setupAppRule =
    SetupAppRule(
      skipOnboarding = true,
      pinCode = listOf(1, 2, 3, 4, 5),
      digidAuthenticated = true,
    )

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun pinCodeForgotTest() {
    launchActivity<MainActivity>().use {
      PinCodeLoginScreenRobot(composeTestRule)
        .clickForgotPinCodeButton()
        .clickCreateNewAccount()
        .clickCreateNewAccountDialogConfirmButton()
        .gotoPinCodeDeletedScreen()
        .clickConfirmButton()
        .gotoPinCodeCreateScreen()
        .assertIsDisplayed()
    }
  }
}
