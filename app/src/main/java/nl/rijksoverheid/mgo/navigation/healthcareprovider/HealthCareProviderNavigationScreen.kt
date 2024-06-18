package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class HealthCareProviderNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Overview : HealthCareProviderNavigationScreen(name = "healthcareprovider-start")

    data object Details : HealthCareProviderNavigationScreen(
        name = "healthcareprovider-details",
        placeholders = listOf("providerId", "providerName", "providerCategory"),
    ) {
        fun setProviderId(id: String): Details {
            builder.addArgument(placeholders[0], id)
            return this
        }

        fun setProviderName(name: String): Details {
            builder.addArgument(placeholders[1], name)
            return this
        }

        fun setProviderCategory(category: String): Details {
            builder.addArgument(placeholders[2], category)
            return this
        }

        fun getProviderId(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
        }

        fun getProviderName(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[1]))
        }

        fun getProviderCategory(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[2]))
        }
    }

    data object RemoveProvider : HealthCareProviderNavigationScreen(
        name = "healthcareprovider-remove",
        placeholders = listOf("providerId", "providerName"),
    ) {
        fun setProviderId(providerId: String): RemoveProvider {
            builder.addArgument(placeholders[0], providerId)
            return this
        }

        fun setProviderName(providerName: String): RemoveProvider {
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

    data object Medication : HealthCareProviderNavigationScreen(name = "medication", placeholders = listOf("providerName")) {
        fun setProviderName(name: String): Medication {
            builder.addArgument(placeholders[0], name)
            return this
        }

        fun getProviderName(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
        }
    }

    data object Concern : HealthCareProviderNavigationScreen(name = "concern")

    data object LaboratoryTestResult : HealthCareProviderNavigationScreen(name = "laboratory-test-result")
}
