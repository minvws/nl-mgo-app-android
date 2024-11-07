package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import kotlinx.serialization.Serializable

@Serializable
sealed class BottomBarNavigation {
    @Serializable
    data object Overview: BottomBarNavigation()

    @Serializable
    data object Organizations: BottomBarNavigation()

    @Serializable
    data object AboutThisApp: BottomBarNavigation()

    @Serializable
    data object Test: BottomBarNavigation()
}
