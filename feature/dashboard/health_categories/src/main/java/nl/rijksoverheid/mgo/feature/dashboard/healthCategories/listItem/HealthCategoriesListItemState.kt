package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

/**
 * Represents the state a [HealthCategoriesListItem] can have.
 */
enum class HealthCategoriesListItemState {
  /**
   * List item is loading the health care data.
   */
  LOADING,

  /**
   * List item has loaded and contains health care data.
   */

  LOADED,

  /**
   * List item has loaded and does not contain health care data.ß
   */
  NO_DATA,
}
