package nl.rijksoverheid.mgo.feature.localisation.organizationSearch.automatic

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OrganizationAutomaticSearchScreenViewState(
    val loading: Boolean,
    val results: List<MgoOrganization>,
    val error: Throwable?,
) {
    companion object {
        val initialState =
            OrganizationAutomaticSearchScreenViewState(
                loading = true,
                results = listOf(),
                error = null,
            )
    }
}
