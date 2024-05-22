package nl.rijksoverheid.mgo.navigation

import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

sealed class BottomBarNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : RootNavigationScreen("dashboard-start")

    data object BottomBar : RootNavigationScreen("dashboard-bottombar")
}
