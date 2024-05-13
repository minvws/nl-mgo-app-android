package nl.rijksoverheid.mgo.feature.localisation.navigation

import androidx.lifecycle.SavedStateHandle
import nl.rijksoverheid.mgo.framework.navigation.BaseNavigationScreen

internal sealed class LocalisationNavigationScreen(name: String, placeholders: List<String> = listOf()) : BaseNavigationScreen(
    name,
    placeholders,
) {
    data object Search : LocalisationNavigationScreen("search")

    data object SearchResults : LocalisationNavigationScreen(name = "getSearchResults", placeholders = listOf("name", "city")) {
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

    data object StoredHealthCareProviders : LocalisationNavigationScreen(name = "storedHealthCareProviders")
}
