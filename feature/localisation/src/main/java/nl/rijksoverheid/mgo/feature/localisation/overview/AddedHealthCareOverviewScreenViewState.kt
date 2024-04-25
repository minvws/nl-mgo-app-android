package nl.rijksoverheid.mgo.feature.localisation.overview

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

data class AddedHealthCareOverviewScreenViewState(
    val providers: List<HealthCareProvider>,
) {
    companion object {
        val initialState = AddedHealthCareOverviewScreenViewState(listOf())
    }
}
