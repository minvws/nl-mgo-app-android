package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

data class HealthCategoriesScreenViewState(
    val name: String,
    val providers: List<MgoOrganization>,
) {
    companion object {
        fun initialState(providers: List<MgoOrganization>) =
            HealthCategoriesScreenViewState(
                name = "Wendy de Bruijn",
                providers = providers,
            )
    }
}
