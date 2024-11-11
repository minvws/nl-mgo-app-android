package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import kotlinx.serialization.Serializable

internal sealed class BottomBarItemNavigation {
    @Serializable
    data object Overview : BottomBarItemNavigation()

    @Serializable
    data object Organizations : BottomBarItemNavigation()

    @Serializable
    data object AboutThisApp : BottomBarItemNavigation()
}
