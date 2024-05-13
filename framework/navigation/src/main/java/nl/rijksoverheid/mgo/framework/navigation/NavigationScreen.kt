package nl.rijksoverheid.mgo.framework.navigation

sealed class NavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : BaseNavigationScreen(
    name,
    placeholders,
) {
    data object Onboarding : NavigationScreen("onboarding")

    data object Dashboard : NavigationScreen("dashboard")

    data object Localisation : NavigationScreen("localisation")

    data object UpdatedRequired : NavigationScreen("updatedRequired")
}
