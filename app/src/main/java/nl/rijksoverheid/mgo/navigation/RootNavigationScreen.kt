package nl.rijksoverheid.mgo.navigation

import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

sealed class RootNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Onboarding : RootNavigationScreen("onboarding")

    data object Dashboard : RootNavigationScreen("dashboard")

    sealed class NewDashboard(name: String, placeholders: List<String> = listOf()) : RootNavigationScreen(name, placeholders) {
        data object BottomBar : RootNavigationScreen("dashboard-bottombar")
    }

    data object Localisation : RootNavigationScreen("localisation")

    data object UpdatedRequired : RootNavigationScreen("updatedRequired")
}
