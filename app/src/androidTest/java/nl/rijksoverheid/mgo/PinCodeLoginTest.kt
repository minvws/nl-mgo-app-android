package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after logging in (= entering a valid pin code), the dashboard is showing.
 */
class PinCodeLoginTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun pinCodeLoginTest() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
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
