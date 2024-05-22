package nl.rijksoverheid.mgo.feature.dashboard.overview

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

data class OverviewScreenViewState(
    val name: String,
    val providers: List<HealthCareProvider>,
) {
    companion object {
        fun initialState(providers: List<HealthCareProvider>) =
            OverviewScreenViewState(
                name = "Wendy de Bruijn",
                providers = providers,
            )
    }
}
