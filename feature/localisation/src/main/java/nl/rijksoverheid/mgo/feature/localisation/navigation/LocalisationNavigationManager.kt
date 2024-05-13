package nl.rijksoverheid.mgo.feature.localisation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class LocalisationNavigationManager(private val navController: NavController) : NavigationManager<LocalisationNavigationScreen> {
    override fun navigate(screen: LocalisationNavigationScreen) {
        when (screen) {
            is LocalisationNavigationScreen.Search -> {
                navController.navigate(screen.getNavigationRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            else -> {
                navController.navigate(screen.getNavigationRoute())
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun getBackStackEntry(screen: LocalisationNavigationScreen): NavBackStackEntry {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
