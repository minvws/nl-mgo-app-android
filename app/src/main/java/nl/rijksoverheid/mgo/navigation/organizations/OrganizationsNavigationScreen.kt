package nl.rijksoverheid.mgo.navigation.organizations

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class OrganizationsNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(name, placeholders) {
    data object Start : OrganizationsNavigationScreen(name = "organizations-start")

    data object RemoveOverview : OrganizationsNavigationScreen(
        name = "organizations-remove",
        placeholders = listOf("providerId", "providerName"),
    ) {
        fun setProviderId(providerId: String): RemoveOverview {
            builder.addArgument(placeholders[0], providerId)
            return this
        }

        fun setProviderName(providerName: String): RemoveOverview {
            builder.addArgument(placeholders[1], providerName)
            return this
        }

        fun getProviderId(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
        }

        fun getProviderName(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[1]))
        }
    }
}
