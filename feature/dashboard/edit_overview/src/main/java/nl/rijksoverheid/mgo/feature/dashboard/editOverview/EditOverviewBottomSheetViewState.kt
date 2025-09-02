package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

data class EditOverviewBottomSheetViewState(
  val favorites: List<HealthCareCategoryId>,
  val nonFavorites: List<HealthCareCategoryId>,
)
