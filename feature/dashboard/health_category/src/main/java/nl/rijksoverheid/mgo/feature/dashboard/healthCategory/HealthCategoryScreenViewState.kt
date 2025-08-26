package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

/**
 * The view state for [HealthCategoryScreen].
 *
 * @param category The [HealthCareCategoryId].
 * @param showErrorBanner If a error banner needs to be shown.
 * @param listItemsState The [ListItemsState].
 */
internal data class HealthCategoryScreenViewState(
  val category: HealthCareCategoryId,
  val showErrorBanner: Boolean,
  val listItemsState: ListItemsState,
) {
  /**
   * Represents the state of the list item.
   */
  internal sealed class ListItemsState {
    /**
     * Indicates that the list item is loading.
     */
    data object Loading : ListItemsState()

    /**
     * Indicates that the list item is loaded.
     *
     * @param listItemsGroup A list of [HealthCategoryScreenListItemsGroup].
     */
    data class Loaded(
      val listItemsGroup: List<HealthCategoryScreenListItemsGroup>,
    ) : ListItemsState()

    /**
     * Indicates that there is no data to display.
     */
    data object NoData : ListItemsState()
  }

  companion object {
    fun initialState(category: HealthCareCategoryId): HealthCategoryScreenViewState =
      HealthCategoryScreenViewState(
        category = category,
        showErrorBanner = false,
        listItemsState = ListItemsState.Loading,
      )
  }
}
