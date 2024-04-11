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
                // You never navigate to the introduction, only to the root of the graph which shows the introduction.
            }

            NavigationScreen.Onboarding.PrivacyOverview -> {
                navController.navigate(NavigationScreen.Onboarding.PrivacyOverview.getRoute())
            }

            NavigationScreen.Config.UpdatedRequired -> {
                navController.navigate(NavigationScreen.Config.UpdatedRequired.getRoute()) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }

            NavigationScreen.Dashboard -> {
                navController.navigate(NavigationScreen.Dashboard.getRoute())
            }

            NavigationScreen.AddHealthCare.Start -> {
                navController.navigate(NavigationScreen.AddHealthCare.Start.getRoute())
            }

            NavigationScreen.AddHealthCare.Search -> {
                // You never navigate to search, only to the root of the graph which shows the search.
            }

            is NavigationScreen.AddHealthCare.GetSearchResults -> {
                navController.navigate(NavigationScreen.AddHealthCare.GetSearchResults.getRoute())
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}
