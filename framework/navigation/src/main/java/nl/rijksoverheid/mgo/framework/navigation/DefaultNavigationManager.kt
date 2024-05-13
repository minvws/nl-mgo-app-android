package nl.rijksoverheid.mgo.framework.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * Default navigation manager that is used by the app.
 */
open class DefaultNavigationManager(private val navController: NavController) : NavigationManager<NavigationScreen> {
    override fun navigate(screen: NavigationScreen) {
        when (screen) {
            NavigationScreen.Onboarding -> {
                navController.navigate(screen.getNavigationRoute())
            }
            NavigationScreen.Dashboard -> {
                navController.navigate(screen.getNavigationRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            NavigationScreen.Localisation -> {
                navController.navigate(screen.getNavigationRoute())
            }
            NavigationScreen.UpdatedRequired -> {
                navController.navigate(screen.getNavigationRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }

    override fun popBackToStart() {
        navController.graph.startDestinationRoute?.let { starDestinationRoute ->
            navController.popBackStack(starDestinationRoute, false)
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun getBackStackEntry(screen: NavigationScreen): NavBackStackEntry {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
