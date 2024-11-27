package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.settings.SettingsScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable

fun NavGraphBuilder.addDashboardSettingsNavGraph() {
    navigation<DashboardNavigation.Settings.Root>(DashboardNavigation.Settings.Debug) {
        mgoComposable<DashboardNavigation.Settings.Debug>(animate = false) {
            SettingsScreen()
        }
    }
}
