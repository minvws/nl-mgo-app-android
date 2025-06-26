package nl.rijksoverheid.mgo.robots

import androidx.test.core.app.launchActivity
import kotlinx.coroutines.coroutineScope
import nl.rijksoverheid.mgo.AppInitializer
import nl.rijksoverheid.mgo.MainActivity
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import javax.inject.Inject

class LaunchAppRobot
  @Inject
  constructor(
    private val appInitializer: AppInitializer,
    private val authRobot: AuthRobot,
    private val onboardingRobot: OnboardingRobot,
    private val featureToggleRepository: FeatureToggleRepository,
  ) {
    suspend fun launchApp(
      skipOnboarding: Boolean = false,
      pinCode: List<Int>? = null,
      digidAuthenticated: Boolean = false,
      skipPinCodeLogin: Boolean = false,
      block: () -> Unit,
    ) = coroutineScope {
      appInitializer(this)

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

      if (skipPinCodeLogin) {
        featureToggleRepository.set(flagSkipPinFeatureToggle, true)
      }

      launchActivity<MainActivity>().use {
        block()
      }
    }
  }
