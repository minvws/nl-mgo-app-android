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
   * Mark this [HealthCareCategory] as a favorite.
   *
   * @param categoryId The [HealthCareCategoryId].
   * @param favorite True if you want to favorite, false if not.
   */
  suspend fun favorite(
    categoryId: HealthCareCategoryId,
    favorite: Boolean,
  )
}
