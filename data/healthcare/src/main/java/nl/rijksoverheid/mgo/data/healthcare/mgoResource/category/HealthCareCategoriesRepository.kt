package nl.rijksoverheid.mgo.data.healthcare.mgoResource.category

import kotlinx.coroutines.flow.Flow

/**
 * Repository that manages [HealthCareCategory].
 */
interface HealthCareCategoriesRepository {
  /**
   * Observes all [HealthCareCategory]. Updates when the favorite status of a [HealthCareCategory] changes.
   */
  fun observe(): Flow<List<HealthCareCategory>>

  /**
   * Marks a list of [HealthCareCategory] as favorite.
   *
   * @param categories The list of [HealthCareCategoryId] you want to mark as favorite.
   */
  suspend fun setFavorites(categories: List<HealthCareCategoryId>)
}
