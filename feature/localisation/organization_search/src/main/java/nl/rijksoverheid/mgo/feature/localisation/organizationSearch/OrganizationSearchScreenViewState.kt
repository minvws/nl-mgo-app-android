package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

sealed class OrganizationSearchScreenViewState {
    data object Loading : OrganizationSearchScreenViewState()

    data class Success(val name: String, val city: String, val results: List<HealthCareProvider>) : OrganizationSearchScreenViewState()

    data class Error(val isProductionBuild: Boolean, val error: Throwable) : OrganizationSearchScreenViewState()

    companion object {
        val initialState = Loading
    }
}
