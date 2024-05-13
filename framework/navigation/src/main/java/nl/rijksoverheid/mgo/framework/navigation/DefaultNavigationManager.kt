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
                navController.navigate(screen.getRoute())
            }

            NavigationScreen.Config.UpdatedRequired -> {
                navController.navigate(NavigationScreen.Config.UpdatedRequired.getRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }

            NavigationScreen.Dashboard -> {
                navController.navigate(NavigationScreen.Dashboard.getRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }

            NavigationScreen.Localisation.Start -> {
                navController.navigate(NavigationScreen.Localisation.Start.getRoute()) {
                    popUpTo(NavigationScreen.Localisation.Start.getRoute()) {
                        inclusive = true
                    }
                }
            }

            NavigationScreen.Localisation.Search -> {
                // You never navigate to search, only to the root of the graph which shows the search.
            }

            is NavigationScreen.Localisation.SearchResults -> {
                navController.navigate(NavigationScreen.Localisation.SearchResults.getNavigationRoute())
            }

            is NavigationScreen.Localisation.StoredHealthCareProviders -> {
                navController.navigate(NavigationScreen.Localisation.StoredHealthCareProviders.getNavigationRoute())
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun getBackStackEntry(screen: NavigationScreen): NavBackStackEntry {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
