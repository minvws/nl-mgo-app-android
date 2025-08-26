package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import nl.rijksoverheid.mgo.rules.LaunchAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after logging in (= entering a valid pin code), the dashboard is showing.
 */
class PinCodeLoginTest {
  @get:Rule
  val launchAppRule = LaunchAppRule()

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun pinCodeLoginTest() =
    runTest {
      launchAppRule.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
      ) {
        PinCodeLoginScreenRobot(composeTestRule)
          .clickKeyboardNumbers(listOf(1, 2, 3, 4, 5))
          .gotoDashboardBottomBarScreen()
          .assertIsDisplayed()
      }
    }
}
