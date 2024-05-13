package nl.rijksoverheid.mgo.framework.navigation

import androidx.navigation.NavBackStackEntry

interface NavigationManager<NS : BaseNavigationScreen> {
    fun navigate(screen: NS)

    fun popBackStack()

    fun popBackToStart()

    fun getBackStackEntry(screen: NS): NavBackStackEntry?
}
