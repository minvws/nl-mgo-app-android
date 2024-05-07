package nl.rijksoverheid.mgo.feature.localisation.stored

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

data class StoredHealthCareProvidersScreenViewState(
    val providers: List<HealthCareProvider>,
) {
    companion object {
        val initialState = StoredHealthCareProvidersScreenViewState(listOf())
    }
}
