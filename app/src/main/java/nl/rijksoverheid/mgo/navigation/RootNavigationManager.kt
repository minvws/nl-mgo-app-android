package nl.rijksoverheid.mgo.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

/**
 * Default navigation manager that is used by the app.
 */
open class RootNavigationManager(private val navController: NavController) : NavigationManager<RootNavigationScreen> {
    override fun navigate(screen: RootNavigationScreen) {
        when (screen) {
            RootNavigationScreen.Onboarding -> {
                navController.navigate(screen.getNavigationRoute())
            }
            RootNavigationScreen.Dashboard -> {
                navController.navigate(screen.getNavigationRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            RootNavigationScreen.Localisation -> {
                navController.navigate(screen.getNavigationRoute())
            }
            RootNavigationScreen.UpdatedRequired -> {
                navController.navigate(screen.getNavigationRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun getBackStackEntry(screen: RootNavigationScreen): NavBackStackEntry {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
