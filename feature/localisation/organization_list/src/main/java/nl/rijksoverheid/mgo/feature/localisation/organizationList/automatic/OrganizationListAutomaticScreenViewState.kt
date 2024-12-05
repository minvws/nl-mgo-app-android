package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OrganizationListAutomaticScreenViewState(
    val loading: Boolean,
    val results: List<MgoOrganization>,
    val error: Throwable?,
) {
    companion object {
        val initialState =
            OrganizationListAutomaticScreenViewState(
                loading = true,
                results = listOf(),
                error = null,
            )
    }
}
