package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.DashboardBottomBarScreenRobot
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after resetting the app, the introduction screen is shown again.
 */
class ResetAppTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun resetAppTest() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
        DashboardBottomBarScreenRobot(composeTestRule)
          .selectSettingsTab()
          .gotoSettingsHomeScreen()
          .clickResetAppButton()
          .clickResetAppDialogButton()
          .gotoIntroductionScreen()
          .assertIsDisplayed()
      }
    }
}
