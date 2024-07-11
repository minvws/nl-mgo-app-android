package nl.rijksoverheid.mgo.navigation.onboarding

import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class OnboardingNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : OnboardingNavigationScreen("onboarding-start")

    data object Introduction : OnboardingNavigationScreen("onboarding-introduction")

    data object Proposition : OnboardingNavigationScreen("proposition")
}
