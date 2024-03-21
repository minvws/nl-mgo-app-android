package nl.rijksoverheid.mgo.framework.navigation

/**
 * Empty navigation manager that exists so that Jetpack Compose Previews and snapshot tests work.
 */
class EmptyNavigationManager : NavigationManager {
    override fun navigate(screen: NavigationScreen) {
    }

    override fun popBackStack() {
    }
}
