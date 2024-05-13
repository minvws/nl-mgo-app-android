package nl.rijksoverheid.mgo.framework.navigation

import androidx.lifecycle.SavedStateHandle

sealed class NavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : BaseNavigationScreen(
    name,
    placeholders,
) {
    open fun getNavigationRoute(): String {
        return builder.buildRoute()
    }

    data object Onboarding : NavigationScreen("onboarding")

    sealed class Localisation(name: String, placeholders: List<String> = listOf()) : NavigationScreen(name, placeholders) {
        data object Start : Localisation("start")

        data object Search : Localisation("search")

        data object SearchResults : Localisation(name = "getSearchResults", placeholders = listOf("name", "city")) {
            fun setName(name: String): SearchResults {
                builder.addArgument(placeholders[0], name)
                return this
            }

            fun setCity(city: String): SearchResults {
                builder.addArgument(placeholders[1], city)
                return this
            }

            fun getName(savedStateHandle: SavedStateHandle): String {
                return requireNotNull(savedStateHandle[placeholders[0]])
            }

            fun getCity(savedStateHandle: SavedStateHandle): String {
                return requireNotNull(savedStateHandle[placeholders[1]])
            }
        }

        data object StoredHealthCareProviders : Localisation(name = "storedHealthCareProviders")
    }

    sealed class Config(name: String, placeholders: List<String> = listOf()) : NavigationScreen(name, placeholders) {
        data object UpdatedRequired : NavigationScreen("updatedRequired")
    }

    data object Dashboard : NavigationScreen("dashboard")
}
