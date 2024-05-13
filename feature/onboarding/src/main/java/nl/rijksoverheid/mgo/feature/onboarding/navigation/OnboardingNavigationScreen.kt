package nl.rijksoverheid.mgo.feature.onboarding.navigation

import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

internal sealed class OnboardingNavigationScreen(name: String, placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Introduction : OnboardingNavigationScreen("introduction")

    data object PrivacyOverview : OnboardingNavigationScreen("privacyOverview")
}
