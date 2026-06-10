package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FEATURE_TOGGLE_SKIP_DIGID_LOGIN
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggleRepository
import nl.rijksoverheid.mgo.reset.ResetApp
import javax.inject.Inject

class AppInitializer
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
    private val setDigidAuthenticated: SetDigidAuthenticated,
    private val resetApp: ResetApp,
  ) {
    /**
     * Can be used to set a certain state of the app when launching. Useful for e2e tests.
     */
    @VisibleForTesting
    fun override(
      skipOnboarding: Boolean = false,
      digidAuthenticated: Boolean = false,
      skipDigidLogin: Boolean = false,
    ) {
      resetApp.invoke()
      if (skipOnboarding) {
        setHasSeenOnboarding(true)
      }

      if (digidAuthenticated) {
        setDigidAuthenticated()
      }

      if (skipDigidLogin) {
        featureToggleRepository.set(FEATURE_TOGGLE_SKIP_DIGID_LOGIN, true)
      }
    }

    /**
     * Clear certain state of the app after calling [override]. Useful for e2e tests.
     */
    @VisibleForTesting
    fun clear() {
      resetApp.invoke()
    }
  }
