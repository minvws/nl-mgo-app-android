package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.settings.display.SettingsDisplayScreen
import nl.rijksoverheid.mgo.feature.settings.home.SettingsHomeScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable

/**
 * Adds all the navigation destinations that can be found in the settings bottom bar tab in the dashboard.
 */
fun NavGraphBuilder.addDashboardSettingsNavGraph(navController: NavController) {
    navigation<DashboardNavigation.Settings.Root>(DashboardNavigation.Settings.Home) {
        mgoComposable<DashboardNavigation.Settings.Home>(animate = false) {
            SettingsHomeScreen(
                onNavigateToDisplaySettings = {
                    navController.navigate(DashboardNavigation.Settings.Display)
                },
                onNavigateToSecuritySettings = {},
                onNavigateToAdvancedSettings = {},
                onNavigateToAboutThisAppSettings = {},
                onNavigateToOnboarding = {},
            )
        }

        mgoComposable<DashboardNavigation.Settings.Display> {
            SettingsDisplayScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
