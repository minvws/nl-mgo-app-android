package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import nl.rijksoverheid.mgo.robots.AuthRobot
import nl.rijksoverheid.mgo.robots.OnboardingRobot
import nl.rijksoverheid.mgo.robots.PinCodeLoginScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class PinCodeForgotTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Inject
  lateinit var authRobot: AuthRobot

  @Inject
  lateinit var onboardingRobot: OnboardingRobot

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun pinCodeForgotTest() {
    onboardingRobot
      .skipOnboarding()

    authRobot
      .setAuthenticatedWithDigid()
      .setPinCode(listOf(1, 2, 3, 4, 5))

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
