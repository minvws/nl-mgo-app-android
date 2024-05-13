package nl.rijksoverheid.mgo.feature.onboarding.navigation

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class EmptyOnboardingNavigationManager : NavigationManager<OnboardingNavigationScreen> {
    override fun navigate(screen: OnboardingNavigationScreen) {
    }

    override fun popBackStack() {
    }

    override fun popBackToStart() {
    }

    override fun getBackStackEntry(screen: OnboardingNavigationScreen): NavBackStackEntry? {
        return null
    }
}
