package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.component.error.ErrorBannerState
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
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

    sealed class Error : ListItemsState() {
      data object UserError : Error()

      data object ServerError : Error()
    }
  }
}

internal fun HealthCategoryScreenViewState.ListItemsState.getMgoResources(): List<MgoResource> =
  (this as? HealthCategoryScreenViewState.ListItemsState.Loaded)
    ?.listItemsGroup
    ?.flatMap { group ->
      group.items
    }?.map { item -> item.mgoResource } ?: listOf()
