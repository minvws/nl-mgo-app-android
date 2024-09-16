package nl.rijksoverheid.mgo.navigation.organization

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.feature.dashboard.overview.HealthCategoriesScreenType
import nl.rijksoverheid.mgo.framework.test.jsonStringToObject
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class OverviewNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(
        name,
        placeholders,
    ) {
    data object Start : OverviewNavigationScreen(name = "overview-start", placeholders = listOf("screenType")) {
        fun setScreenType(screenType: HealthCategoriesScreenType): Start {
            val json = screenType.toJsonString()
            builder.addArgument(placeholders[0], json)
            return this
        }

        fun getScreenType(backStackEntry: NavBackStackEntry): HealthCategoriesScreenType {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0])?.jsonStringToObject())
        }
    }

    data object HealthCategory : OverviewNavigationScreen(name = "overview-healthCategory")

    data object UiSchemaDetail : OverviewNavigationScreen(
        name = "overview-uiSchemaDetail",
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
