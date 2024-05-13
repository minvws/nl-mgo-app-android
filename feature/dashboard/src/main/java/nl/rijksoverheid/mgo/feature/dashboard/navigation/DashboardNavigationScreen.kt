package nl.rijksoverheid.mgo.feature.dashboard.navigation

import nl.rijksoverheid.mgo.framework.navigation.BaseNavigationScreen

internal sealed class DashboardNavigationScreen(name: String, placeholders: List<String> = listOf()) : BaseNavigationScreen(
    name,
    placeholders,
) {
    data object Start : DashboardNavigationScreen("start")

    data object Detail : DashboardNavigationScreen("detail")
}
