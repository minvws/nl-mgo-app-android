package nl.rijksoverheid.mgo.navigation.localisation

import kotlinx.serialization.Serializable

sealed class LocalisationNavigation {
    @Serializable
    data object Root : LocalisationNavigation()

    @Serializable
    data object AddOrganization : LocalisationNavigation()

    @Serializable
    data class OrganisationListManual(val name: String, val city: String) : LocalisationNavigation()

    @Serializable
    data object OrganizationListAutomatic : LocalisationNavigation()
}
