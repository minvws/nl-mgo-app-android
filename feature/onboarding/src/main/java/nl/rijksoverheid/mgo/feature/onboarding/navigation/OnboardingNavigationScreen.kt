package nl.rijksoverheid.mgo.feature.onboarding.navigation

import nl.rijksoverheid.mgo.framework.navigation.BaseNavigationScreen

internal sealed class OnboardingNavigationScreen(name: String, placeholders: List<String> = listOf()) : BaseNavigationScreen(
    name,
    placeholders,
) {
    data object Introduction : OnboardingNavigationScreen("introduction")

    data object PrivacyOverview : OnboardingNavigationScreen("privacyOverview")
}
