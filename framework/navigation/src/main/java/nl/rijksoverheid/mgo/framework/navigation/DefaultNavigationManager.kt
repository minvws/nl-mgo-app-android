package nl.rijksoverheid.mgo.framework.navigation

import androidx.navigation.NavController

/**
 * Default navigation manager that is used by the app.
 */
class DefaultNavigationManager(private val navController: NavController) : NavigationManager {
    override fun navigate(screen: NavigationScreen) {
        when (screen) {
            NavigationScreen.Onboarding.Start -> {
                navController.navigate(NavigationScreen.Onboarding.Introduction.getRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }

            NavigationScreen.Onboarding.Introduction -> {
                // You never navigate to the start of the onboarding, only to the root of the graph which shows the start.
            }

            NavigationScreen.Onboarding.PrivacyOverview -> {
                navController.navigate(NavigationScreen.Onboarding.PrivacyOverview.getRoute())
            }

            NavigationScreen.Error.NoInternet -> TODO()

            NavigationScreen.Splash -> {
                navController.navigate(NavigationScreen.Splash.getRoute())
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}
