package nl.rijksoverheid.mgo.feature.localisation.organizationList

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

internal data class OrganizationListScreenViewState(
    val providers: List<HealthCareProvider>,
) {
    companion object {
        val initialState = OrganizationListScreenViewState(listOf())
    }
}
