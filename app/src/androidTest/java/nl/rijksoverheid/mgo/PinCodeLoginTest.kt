package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import nl.rijksoverheid.mgo.rules.SetupAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after logging in (= entering a valid pin code), the dashboard is showing.
 */
class PinCodeLoginTest {
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
  fun pinCodeLoginTest() {
    launchActivity<MainActivity>().use {
      PinCodeLoginScreenRobot(composeTestRule)
        .clickKeyboardNumbers(listOf(1, 2, 3, 4, 5))
        .gotoDashboardBottomBarScreen()
        .assertIsDisplayed()
    }
  }
}
