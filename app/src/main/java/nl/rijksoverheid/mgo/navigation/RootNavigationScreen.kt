package nl.rijksoverheid.mgo.navigation

import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

sealed class RootNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Onboarding : RootNavigationScreen("onboarding")

    data object Dashboard : RootNavigationScreen("dashboard")

    data object Localisation : RootNavigationScreen("localisation")

    data object UpdatedRequired : RootNavigationScreen("updatedRequired")
}
