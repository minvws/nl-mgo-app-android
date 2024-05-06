package nl.rijksoverheid.mgo.framework.navigation

import androidx.navigation.NavBackStackEntry

interface NavigationManager {
    fun navigate(screen: NavigationScreen)

    fun popBackStack()

    fun getBackStackEntry(screen: NavigationScreen): NavBackStackEntry?
}
