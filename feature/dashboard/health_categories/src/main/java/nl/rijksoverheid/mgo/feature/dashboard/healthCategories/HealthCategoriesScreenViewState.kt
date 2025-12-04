package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner.HealthCategoriesBannerState

data class HealthCategoriesScreenViewState(
  val name: String,
  val providers: List<MgoOrganization>,
  val favorites: List<HealthCategoryGroup.HealthCategory>,
  val groups: List<HealthCategoryGroup>,
  val automaticLocalisationEnabled: Boolean,
  val banner: HealthCategoriesBannerState?,
) {
  companion object {
    fun initialState(
      favorites: List<HealthCategoryGroup.HealthCategory>,
      groups: List<HealthCategoryGroup>,
      providers: List<MgoOrganization>,
      automaticLocalisationEnabled: Boolean,
    ) = HealthCategoriesScreenViewState(
      name = "Wendy de Bruijn",
      providers = providers,
      automaticLocalisationEnabled = automaticLocalisationEnabled,
      favorites = favorites,
      groups = groups,
      banner = HealthCategoriesBannerState.Loading,
    )
  }
}
