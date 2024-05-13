package nl.rijksoverheid.mgo.feature.onboarding.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class OnboardingNavigationManager(private val navController: NavController) : NavigationManager<OnboardingNavigationScreen> {
    override fun navigate(screen: OnboardingNavigationScreen) {
        navController.navigate(screen.getNavigationRoute())
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun popBackToStart() {
    }

    override fun getBackStackEntry(screen: OnboardingNavigationScreen): NavBackStackEntry {
        return navController.getBackStackEntry(screen.getRoute())
    }
}
