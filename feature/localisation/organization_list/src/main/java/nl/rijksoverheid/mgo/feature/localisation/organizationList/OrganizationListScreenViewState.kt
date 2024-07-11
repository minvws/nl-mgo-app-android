package nl.rijksoverheid.mgo.feature.localisation.organizationList

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

internal data class OrganizationListScreenViewState(
    val providers: List<MgoOrganization>,
) {
    companion object {
        val initialState = OrganizationListScreenViewState(listOf())
    }
}
