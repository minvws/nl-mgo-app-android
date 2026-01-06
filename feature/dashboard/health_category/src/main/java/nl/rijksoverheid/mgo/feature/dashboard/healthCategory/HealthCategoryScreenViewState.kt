package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.component.error.ErrorBannerState
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

internal data class HealthCategoryScreenViewState(
  val category: HealthCategoryGroup.HealthCategory,
  val listItemsState: ListItemsState,
  val banner: ErrorBannerState?,
) {
  internal sealed class ListItemsState {
    data object Loading : ListItemsState()

    data class Loaded(
      val listItemsGroup: List<HealthCategoryScreenListItemsGroup>,
    ) : ListItemsState()

    data object NoData : ListItemsState()
  }
}
