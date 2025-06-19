package nl.rijksoverheid.mgo.robots

import androidx.test.core.app.launchActivity
import nl.rijksoverheid.mgo.MainActivity
import javax.inject.Inject

class LaunchAppRobot
  @Inject
  constructor(
    private val authRobot: AuthRobot,
    private val onboardingRobot: OnboardingRobot,
  ) {
    fun launchApp(
      skipOnboarding: Boolean = false,
      pinCode: List<Int>? = null,
      digidAuthenticated: Boolean = false,
      block: () -> Unit,
    ) {
      if (skipOnboarding) {
        onboardingRobot
          .skipOnboarding()
      }

      if (pinCode != null) {
        authRobot.setPinCode(pinCode)
      }

      if (digidAuthenticated) {
        authRobot.setAuthenticatedWithDigid()
      }

      launchActivity<MainActivity>().use {
        block()
      }
    }
  }
