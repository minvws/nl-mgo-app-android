package nl.rijksoverheid.mgo.framework.navigation

import androidx.navigation.NavController

/**
 * Default navigation manager that is used by the app.
 */
class DefaultNavigationManager(private val navController: NavController) : NavigationManager {
    override fun navigate(screen: NavigationScreen) {
        when (screen) {
            NavigationScreen.Onboarding.Introduction -> TODO()
            NavigationScreen.Onboarding.PrivacyOverview -> TODO()
            NavigationScreen.Onboarding.PrivacyStatement -> TODO()
            NavigationScreen.Onboarding.Start -> {
                navController.navigate(NavigationScreen.Onboarding.Introduction.getRoute())
            }

            NavigationScreen.Splash -> {
                navController.navigate(NavigationScreen.Splash.getRoute())
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}
