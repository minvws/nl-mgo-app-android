package nl.rijksoverheid.mgo.navigation.onboarding

import kotlinx.serialization.Serializable

sealed class OnboardingNavigation {
    @Serializable
    object Root : OnboardingNavigation()

    @Serializable
    object Introduction : OnboardingNavigation()

    @Serializable
    object Proposition : OnboardingNavigation()
}
