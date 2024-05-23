package nl.rijksoverheid.mgo.navigation.dashboard

import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class DashboardNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : DashboardNavigationScreen("dashboard-start")

    data object BottomBar : DashboardNavigationScreen("dashboard-bottombar")
}
