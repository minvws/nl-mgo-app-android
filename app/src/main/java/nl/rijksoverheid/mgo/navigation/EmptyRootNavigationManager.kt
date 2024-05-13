package nl.rijksoverheid.mgo.navigation

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

/**
 * Empty navigation manager that exists so that Jetpack Compose Previews and snapshot tests work.
 */
class EmptyRootNavigationManager : NavigationManager<RootNavigationScreen> {
    override fun navigate(screen: RootNavigationScreen) {
    }

    override fun popBackStack() {
    }

    override fun getBackStackEntry(screen: RootNavigationScreen): NavBackStackEntry? {
        return null
    }
}
