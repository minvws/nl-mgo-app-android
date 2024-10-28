package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import nl.rijksoverheid.mgo.framework.test.jsonStringToObject
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class DashboardNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : DashboardNavigationScreen("dashboard-start")

    data object BottomBar : DashboardNavigationScreen("dashboard-bottombar")

    data object HealthCategories : DashboardNavigationScreen(name = "dashboard-health-categories", placeholders = listOf("organization")) {
        fun setOrganization(organization: MgoOrganization): DashboardNavigationScreen {
            val json = organization.toJsonString()
            builder.addArgument(placeholders[0], json)
            return this
        }

        fun getOrganization(backStackEntry: NavBackStackEntry): MgoOrganization {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0])?.jsonStringToObject())
        }
    }

    data object HealthCategory : DashboardNavigationScreen(name = "dashboard-health-category", placeholders = listOf("arguments")) {
        fun setArguments(arguments: HealthCategoryScreenArguments): HealthCategory {
            val json = arguments.toJsonString()
            builder.addArgument(placeholders[0], json)
            return this
        }

        fun getArguments(backStackEntry: NavBackStackEntry): HealthCategoryScreenArguments {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0])?.jsonStringToObject())
        }
    }

    data object UiSchemaDetail : DashboardNavigationScreen(
        name = "dashboard-ui-schema-detail",
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

    data object RemoveOrganization : DashboardNavigationScreen(
        name = "dashboard-remove-orgranization",
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
}
