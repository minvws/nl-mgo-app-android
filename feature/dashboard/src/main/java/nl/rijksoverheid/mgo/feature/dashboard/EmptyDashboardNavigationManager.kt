package nl.rijksoverheid.mgo.feature.dashboard

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

class EmptyDashboardNavigationManager : NavigationManager<DashboardNavigationScreen> {
    override fun navigate(screen: DashboardNavigationScreen) {
    }

    override fun popBackStack() {
    }

    override fun getBackStackEntry(screen: DashboardNavigationScreen): NavBackStackEntry? {
        return null
    }
}
