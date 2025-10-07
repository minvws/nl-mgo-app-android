package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.StringRes

/**
 * Represents a group of [HealthCategoryScreenListItem] to show in [HealthCategoryScreen].
 *
 * @param heading The string resource to show as heading.
 * @param items The list of [HealthCategoryScreenListItem] that fall under this group.
 */
data class HealthCategoryScreenListItemsGroup(
  @StringRes val heading: Int,
  val items: List<HealthCategoryScreenListItem>,
)
