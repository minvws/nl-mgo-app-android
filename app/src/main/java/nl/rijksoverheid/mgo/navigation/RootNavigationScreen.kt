package nl.rijksoverheid.mgo.navigation

import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

sealed class RootNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Onboarding : RootNavigationScreen("onboarding")

    data object Dashboard : RootNavigationScreen("dashboard")

    sealed class NewDashboard(name: String, placeholders: List<String> = listOf()) : RootNavigationScreen(name, placeholders) {
        data object Start : RootNavigationScreen("dashboard-start")

        data object BottomBar : RootNavigationScreen("dashboard-bottombar")
    }

    sealed class NewLocalisation(name: String, placeholders: List<String> = listOf()) : RootNavigationScreen(name, placeholders) {
        data object Start : RootNavigationScreen("localisation-start")

        data object Search : RootNavigationScreen("localisation-search")
    }

    data object Localisation : RootNavigationScreen("localisation")

    data object UpdatedRequired : RootNavigationScreen("updatedRequired")
}
