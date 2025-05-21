package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import kotlinx.serialization.Serializable

/**
 * Represents the root navigation of the bottom bar item.
 */
internal sealed class BottomBarItemNavigation {
  @Serializable
  data object Overview : BottomBarItemNavigation()

  @Serializable
  data object Organizations : BottomBarItemNavigation()

  @Serializable
  data object Settings : BottomBarItemNavigation()
}
