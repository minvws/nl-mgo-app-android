package nl.rijksoverheid.mgo.feature.dashboard.navigation

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class EmptyDashboardNavigationManager : NavigationManager<DashboardNavigationScreen> {
    override fun navigate(screen: DashboardNavigationScreen) {
    }

    override fun popBackStack() {
    }

    override fun getBackStackEntry(screen: DashboardNavigationScreen): NavBackStackEntry? {
        return null
    }
}
