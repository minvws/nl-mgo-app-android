package nl.rijksoverheid.mgo.feature.dashboard.organizations

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OrganizationsViewState(
    val organizations: List<MgoOrganization>,
) {
    companion object {
        fun initialState(organizations: List<MgoOrganization>) =
            OrganizationsViewState(
                organizations = organizations,
            )
    }
}
