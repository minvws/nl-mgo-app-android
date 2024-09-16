package nl.rijksoverheid.mgo.feature.dashboard.overview

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class OverviewScreenViewState(
    val name: String,
    val screenType: HealthCategoriesScreenType,
    val providers: List<MgoOrganization>,
) {
    companion object {
        fun initialState(
            screenType: HealthCategoriesScreenType,
            providers: List<MgoOrganization>,
        ) = OverviewScreenViewState(
            name = "Wendy de Bruijn",
            screenType = screenType,
            providers = providers,
        )
    }
}
