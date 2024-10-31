package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class LocalisationNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : LocalisationNavigationScreen("localisation-start")

    data object AddOrganization : LocalisationNavigationScreen("localisation-add-organization")

    data object OrganizationList : LocalisationNavigationScreen(
        name = "localisation-organization-list",
        placeholders =
            listOf(
                "name",
                "city",
            ),
    ) {
        fun setName(name: String): OrganizationList {
            builder.addArgument(placeholders[0], name)
            return this
        }

        fun setCity(city: String): OrganizationList {
            builder.addArgument(placeholders[1], city)
            return this
        }

        fun getName(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
        }

        fun getCity(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[1]))
        }
    }
}
