package nl.rijksoverheid.mgo.navigation

import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

sealed class LocalisationNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : RootNavigationScreen("localisation-start")

    data object Search : RootNavigationScreen("localisation-search")
}
