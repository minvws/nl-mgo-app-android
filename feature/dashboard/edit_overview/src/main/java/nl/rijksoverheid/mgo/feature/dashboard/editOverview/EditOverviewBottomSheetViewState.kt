package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

data class EditOverviewBottomSheetViewState(
  val favorites: List<HealthCategoryGroup.HealthCategory>,
  val nonFavorites: List<HealthCategoryGroup.HealthCategory>,
)
