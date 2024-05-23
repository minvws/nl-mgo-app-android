package nl.rijksoverheid.mgo.navigation.healthcareprovider

import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class HealthCareProviderNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Start : HealthCareProviderNavigationScreen("healthcareprovider-start")

    data object Details : HealthCareProviderNavigationScreen("healthcareprovider-details")
}
