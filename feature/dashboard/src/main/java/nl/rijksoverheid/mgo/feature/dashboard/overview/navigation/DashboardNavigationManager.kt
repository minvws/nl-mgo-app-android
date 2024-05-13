package nl.rijksoverheid.mgo.feature.dashboard.overview.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class DashboardNavigationManager(private val navController: NavController) : NavigationManager<DashboardNavigationScreen> {
    override fun navigate(screen: DashboardNavigationScreen) {
        navController.navigate(screen.getRoute())
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun getBackStackEntry(screen: DashboardNavigationScreen): NavBackStackEntry? {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
