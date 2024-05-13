package nl.rijksoverheid.mgo.framework.navigation

import androidx.navigation.NavBackStackEntry

interface NavigationManager<NS : NavigationScreen> {
    fun navigate(screen: NS)

    fun popBackStack()

    fun getBackStackEntry(screen: NS): NavBackStackEntry?
}
