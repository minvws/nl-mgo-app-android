package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OrganizationSearchScreenViewState(
    val loading: Boolean,
    val name: String,
    val city: String,
    val results: List<MgoOrganization>,
    val error: Throwable?,
) {
    companion object {
        fun initialState(
            name: String,
            city: String,
        ): OrganizationSearchScreenViewState {
            return OrganizationSearchScreenViewState(
                loading = true,
                name = name,
                city = city,
                results = listOf(),
                error = null,
            )
        }
    }
}
