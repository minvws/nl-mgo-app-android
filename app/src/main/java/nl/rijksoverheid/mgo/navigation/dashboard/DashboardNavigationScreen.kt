package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenType
import nl.rijksoverheid.mgo.framework.test.jsonStringToObject
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class DashboardNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : DashboardNavigationScreen("dashboard-start")

    data object BottomBar : DashboardNavigationScreen("dashboard-bottombar")

    data object HealthCategories : DashboardNavigationScreen(name = "dashboard-health-categories", placeholders = listOf("screenType")) {
        fun setScreenType(screenType: HealthCategoriesScreenType): DashboardNavigationScreen {
            val json = screenType.toJsonString()
            builder.addArgument(placeholders[0], json)
            return this
        }

        fun getScreenType(backStackEntry: NavBackStackEntry): HealthCategoriesScreenType {
            return requireNotNull(backStackEntry.arguments?.getString(placeholders[0])?.jsonStringToObject())
        }
    }
}
