package nl.rijksoverheid.mgo.robots

import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import javax.inject.Inject

class OnboardingRobot
  @Inject
  constructor(
    private val hasSeenOnboarding: HasSeenOnboarding,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
  ) {
    fun skipOnboarding(): OnboardingRobot {
      setHasSeenOnboarding(true)
      assertOnboardingSkipped()
      return this
    }

    fun assertOnboardingSkipped(): OnboardingRobot {
      check(hasSeenOnboarding()) { "Onboarding should not be showing" }
      return this
    }
  }
