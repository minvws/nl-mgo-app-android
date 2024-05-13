package nl.rijksoverheid.mgo.feature.dashboard.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class DashboardNavigationManager(private val navController: NavController) : NavigationManager<DashboardNavigationScreen> {
    override fun navigate(screen: DashboardNavigationScreen) {
        navController.navigate(screen.getNavigationRoute())
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun popBackToStart() {
    }

    override fun getBackStackEntry(screen: DashboardNavigationScreen): NavBackStackEntry? {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
