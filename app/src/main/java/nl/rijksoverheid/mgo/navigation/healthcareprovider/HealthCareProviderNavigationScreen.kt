package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.framework.test.jsonStringToObject
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class HealthCareProviderNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Overview : HealthCareProviderNavigationScreen(name = "healthcareprovider-start")

    data object Details : HealthCareProviderNavigationScreen(
        name = "healthcareprovider-details",
        placeholders = listOf("providerJson"),
    ) {
        fun setProvider(provider: HealthCareProvider): Details {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): HealthCareProvider {
            val json = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return json.jsonStringToObject()
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

    data object Medication : HealthCareProviderNavigationScreen(
        name = "medication",
        placeholders = listOf("provider"),
    ) {
        fun setProvider(provider: HealthCareProvider): Medication {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): HealthCareProvider {
            val providerJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return providerJson.jsonStringToObject()
        }
    }

    data object Concern : HealthCareProviderNavigationScreen(name = "concern")

    data object LaboratoryTestResult : HealthCareProviderNavigationScreen(name = "laboratory-test-result")
}
