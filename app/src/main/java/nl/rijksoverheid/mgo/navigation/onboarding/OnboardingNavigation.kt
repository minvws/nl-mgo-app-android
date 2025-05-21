package nl.rijksoverheid.mgo.navigation.onboarding

import kotlinx.serialization.Serializable

/**
 * Represents all navigation destinations when going through the onboarding.
 */
sealed class OnboardingNavigation {
  @Serializable
  data object Root : OnboardingNavigation()

  @Serializable
  data object Introduction : OnboardingNavigation()

  @Serializable
  data object Proposition : OnboardingNavigation()
}
