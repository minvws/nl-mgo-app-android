package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.settings.home.SettingsHomeScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable

/**
 * Adds all the navigation destinations that can be found in the settings bottom bar tab in the dashboard.
 */
fun NavGraphBuilder.addDashboardSettingsNavGraph() {
    navigation<DashboardNavigation.Settings.Root>(DashboardNavigation.Settings.Debug) {
        mgoComposable<DashboardNavigation.Settings.Debug>(animate = false) {
            SettingsHomeScreen(
                onNavigateToDisplaySettings = {},
                onNavigateToSecuritySettings = {},
                onNavigateToAdvancedSettings = {},
                onNavigateToAboutThisAppSettings = {},
                onNavigateToOnboarding = {},
            )
        }
    }
}
