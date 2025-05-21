package nl.rijksoverheid.mgo.navigation.localisation

import kotlinx.serialization.Serializable

/**
 * Represents all navigation destinations when searching for health care providers.
 */
@Serializable
sealed class LocalisationNavigation {
  @Serializable
  data class Root(val checkResults: Boolean) : LocalisationNavigation()

  @Serializable
  data object AddOrganization : LocalisationNavigation()

  @Serializable
  data class OrganisationListManual(val name: String, val city: String) : LocalisationNavigation()

  @Serializable
  data class OrganizationListAutomatic(val checkResults: Boolean) : LocalisationNavigation()
}
