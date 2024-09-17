package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.copy.R

data class HealthCategoriesScreenViewState(
    val name: String,
    val filterOrganization: MgoOrganization?,
    val providers: List<MgoOrganization>,
) {
    companion object {
        fun initialState(
            filterOrganization: MgoOrganization?,
            providers: List<MgoOrganization>,
        ) = HealthCategoriesScreenViewState(
            name = "Wendy de Bruijn",
            filterOrganization = filterOrganization,
            providers = providers,
        )
    }

    @Composable
    fun getToolbarTitle(): String {
        return filterOrganization?.name ?: stringResource(R.string.overview_heading)
    }
}
