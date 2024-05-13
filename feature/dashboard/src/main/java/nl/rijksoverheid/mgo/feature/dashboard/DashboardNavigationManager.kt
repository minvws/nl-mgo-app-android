package nl.rijksoverheid.mgo.feature.dashboard

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class DashboardNavigationManager(private val navController: NavController) : NavigationManager<DashboardNavigationScreen> {
    override fun navigate(screen: DashboardNavigationScreen) {
        TODO("Not yet implemented")
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun getBackStackEntry(screen: DashboardNavigationScreen): NavBackStackEntry? {
        return null
    }
}
