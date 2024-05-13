package nl.rijksoverheid.mgo.feature.localisation.navigation

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal class EmptyLocalisationNavigationManager : NavigationManager<LocalisationNavigationScreen> {
    override fun navigate(screen: LocalisationNavigationScreen) {
    }

    override fun popBackStack() {
    }

    override fun popBackToStart() {
    }

    override fun getBackStackEntry(screen: LocalisationNavigationScreen): NavBackStackEntry? {
        return null
    }
}
