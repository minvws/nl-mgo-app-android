package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup

import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItem

data class ListItemsGroup(
  val heading: String?,
  val items: List<HealthCategoryScreenListItem>,
)
