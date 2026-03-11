package nl.rijksoverheid.mgo.navigation.localisation

import kotlinx.serialization.Serializable

/**
 * Represents all navigation destinations when searching for health care providers.
 */
@Serializable
sealed class LocalisationNavigation {
  @Serializable
  data object Root : LocalisationNavigation()

  @Serializable
  data object Manual : LocalisationNavigation()
}
