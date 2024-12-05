package nl.rijksoverheid.mgo.feature.dashboard.organizations

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OrganizationsViewState(
    val organizations: List<MgoOrganization>,
    val automaticLocalisationEnabled: Boolean,
) {
    companion object {
        fun initialState(
            organizations: List<MgoOrganization>,
            automaticLocalisationEnabled: Boolean,
        ) = OrganizationsViewState(
            organizations = organizations,
            automaticLocalisationEnabled = automaticLocalisationEnabled,
        )
    }
}
