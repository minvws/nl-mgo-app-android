package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after going through the pin code forgot flow, the pin code create screen is showing.
 */
class PinCodeForgotTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun pinCodeForgotTest() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
      ) {
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
