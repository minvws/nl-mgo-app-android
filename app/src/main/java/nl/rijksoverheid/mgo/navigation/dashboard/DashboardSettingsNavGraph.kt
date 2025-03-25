package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.settings.about.home.SettingsAboutHomeScreen
import nl.rijksoverheid.mgo.feature.settings.advanced.SettingsAdvancedScreen
import nl.rijksoverheid.mgo.feature.settings.display.SettingsDisplayScreen
import nl.rijksoverheid.mgo.feature.settings.home.SettingsHomeScreen
import nl.rijksoverheid.mgo.feature.settings.security.SettingsSecurityScreen
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
                onNavigateToSecuritySettings = {
                    navController.navigate(DashboardNavigation.Settings.Security)
                },
                onNavigateToAdvancedSettings = {
                    navController.navigate(DashboardNavigation.Settings.Advanced)
                },
                onNavigateToAboutThisAppSettings = {
                    navController.navigate(DashboardNavigation.Settings.About.Home)
                },
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

        mgoComposable<DashboardNavigation.Settings.Security> {
            SettingsSecurityScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposable<DashboardNavigation.Settings.Advanced> {
            SettingsAdvancedScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposable<DashboardNavigation.Settings.About.Home> {
            SettingsAboutHomeScreen(
                onNavigateToSecureUse = {},
                onNavigateToOpenSource = {},
                onNavigateToAccessibility = {},
                onNavigateBack = {},
            )
        }
    }
}
