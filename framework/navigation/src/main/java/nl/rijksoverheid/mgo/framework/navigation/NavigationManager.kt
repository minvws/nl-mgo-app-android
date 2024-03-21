package nl.rijksoverheid.mgo.framework.navigation

interface NavigationManager {
    fun navigate(screen: NavigationScreen)

    fun popBackStack()
}
