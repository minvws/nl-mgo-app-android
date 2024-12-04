package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OrganizationListManualScreenViewState(
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
        ): OrganizationListManualScreenViewState {
            return OrganizationListManualScreenViewState(
                loading = true,
                name = name,
                city = city,
                results = listOf(),
                error = null,
            )
        }
    }
}
