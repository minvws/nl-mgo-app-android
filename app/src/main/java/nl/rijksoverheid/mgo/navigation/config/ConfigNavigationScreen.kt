package nl.rijksoverheid.mgo.navigation.config

import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class ConfigNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object UpdateRequired : ConfigNavigationScreen("config-updaterequired")
}
