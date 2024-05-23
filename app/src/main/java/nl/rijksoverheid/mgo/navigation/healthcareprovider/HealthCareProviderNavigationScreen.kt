package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class HealthCareProviderNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Start : HealthCareProviderNavigationScreen(name = "healthcareprovider-start", placeholders = listOf("providerName")) {
        fun setProviderName(name: String): Start {
            builder.addArgument(placeholders[0], name)
            return this
        }
    }

    data object Details : HealthCareProviderNavigationScreen(name = "healthcareprovider-details", placeholders = listOf("providerName")) {
        fun setProviderName(name: String): Details {
            builder.addArgument(placeholders[0], name)
            return this
        }

        fun getProviderName(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
        }
    }
}
