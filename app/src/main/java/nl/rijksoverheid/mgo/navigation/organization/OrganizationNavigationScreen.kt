package nl.rijksoverheid.mgo.navigation.organization

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.test.jsonStringToObject
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class OrganizationNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Overview : OrganizationNavigationScreen(name = "organization-start")

    data object Organization : OrganizationNavigationScreen(
        name = "organization-organization",
        placeholders = listOf("providerJson"),
    ) {
        fun setProvider(provider: MgoOrganization): Organization {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): MgoOrganization {
            val json = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return json.jsonStringToObject()
        }
    }

    data object RemoveProvider : OrganizationNavigationScreen(
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

    data object MedicationUse : OrganizationNavigationScreen(name = "organization-medicationUse", placeholders = listOf("provider")) {
        fun setProvider(provider: MgoOrganization): MedicationUse {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): MgoOrganization {
            val providerJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return providerJson.jsonStringToObject()
        }
    }

    data object Concern : OrganizationNavigationScreen(name = "concern", placeholders = listOf("provider")) {
        fun setProvider(provider: MgoOrganization): Concern {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): MgoOrganization {
            val providerJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return providerJson.jsonStringToObject()
        }
    }

    data object LaboratoryTestResult : OrganizationNavigationScreen(
        name = "laboratory-test-result",
        placeholders = listOf("provider"),
    ) {
        fun setProvider(provider: MgoOrganization): LaboratoryTestResult {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): MgoOrganization {
            val providerJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return providerJson.jsonStringToObject()
        }
    }
}
