package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class HealthCategoriesScreenViewState(
    val name: String,
    val screenType: HealthCategoriesScreenType,
    val providers: List<MgoOrganization>,
) {
    companion object {
        fun initialState(
            screenType: HealthCategoriesScreenType,
            providers: List<MgoOrganization>,
        ) = HealthCategoriesScreenViewState(
            name = "Wendy de Bruijn",
            screenType = screenType,
            providers = providers,
        )
    }
}
