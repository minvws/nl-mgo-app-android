package nl.rijksoverheid.mgo.navigation.organization

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.framework.test.jsonStringToObject
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class OrganizationNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Overview : OrganizationNavigationScreen(name = "organization-start")

    data object RemoveOrganization : OrganizationNavigationScreen(
        name = "organization-remove",
        placeholders = listOf("providerId", "providerName"),
    ) {
        fun setProviderId(providerId: String): RemoveOrganization {
            builder.addArgument(placeholders[0], providerId)
            return this
        }

        fun setProviderName(providerName: String): RemoveOrganization {
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

    data object MedicationUse : OrganizationNavigationScreen(name = "organization-medicationUse")

    data object Problems : OrganizationNavigationScreen(name = "organization-problems", placeholders = listOf("provider")) {
        fun setProvider(provider: MgoOrganization): Problems {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): MgoOrganization {
            val providerJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return providerJson.jsonStringToObject()
        }
    }

    data object LabResults : OrganizationNavigationScreen(
        name = "organization-labResults",
        placeholders = listOf("provider"),
    ) {
        fun setProvider(provider: MgoOrganization): LabResults {
            val providerJson = provider.toJsonString()
            builder.addArgument(placeholders[0], providerJson)
            return this
        }

        fun getProvider(backStackEntry: NavBackStackEntry): MgoOrganization {
            val providerJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
            return providerJson.jsonStringToObject()
        }
    }

    data object UiSchemaDetail : OrganizationNavigationScreen(
        name = "organization-uiSchemaDetail",
        placeholders = listOf("toolbarTitle", "uiSchema"),
    ) {
        fun setToolbarTitle(toolbarTitle: String): UiSchemaDetail {
            builder.addArgument(placeholders[0], toolbarTitle)
            return this
        }

        fun getToolbarTitle(backStackEntry: NavBackStackEntry): String {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0]))
        }

        fun setUiSchema(uiSchema: UISchema): UiSchemaDetail {
            val uiSchemaJson = uiSchema.toJson()
            builder.addArgument(placeholders[1], uiSchemaJson)
            return this
        }

        fun getUiSchema(backStackEntry: NavBackStackEntry): UISchema {
            val uiSchemaJson = requireNotNull(backStackEntry.arguments?.getString(placeholders[1]))
            return UISchema.fromJson(uiSchemaJson)
        }
    }
}
