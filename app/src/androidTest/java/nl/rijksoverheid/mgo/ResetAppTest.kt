package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import nl.rijksoverheid.mgo.robots.DashboardBottomBarScreenRobot
import nl.rijksoverheid.mgo.rules.SetupAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that after resetting the app, the introduction screen is shown again.
 */
class ResetAppTest {
  @get:Rule
  val setupAppRule =
    SetupAppRule(
      skipOnboarding = true,
      pinCode = listOf(1, 2, 3, 4, 5),
      digidAuthenticated = true,
      skipPinCodeLogin = true,
    )

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun resetAppTest() {
    launchActivity<MainActivity>().use {
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
