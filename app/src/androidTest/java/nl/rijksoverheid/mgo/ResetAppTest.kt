package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.DashboardBottomBarScreenRobot
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * This test validates that after resetting the app, the introduction screen is shown again.
 */
@HiltAndroidTest
class ResetAppTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Inject
  lateinit var launchAppRobot: LaunchAppRobot

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun resetAppTest() =
    runTest {
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
