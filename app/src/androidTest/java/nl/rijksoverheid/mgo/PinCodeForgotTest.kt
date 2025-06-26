package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * This test validates that after going through the pin code forgot flow, the pin code create screen is showing.
 */
@HiltAndroidTest
class PinCodeForgotTest {
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
  fun pinCodeForgotTest() =
    runTest {
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
