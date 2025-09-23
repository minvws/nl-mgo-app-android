package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData

/**
 * Repository for accessing and observing health data.
 */
interface HealthDataRepository {
  /**
   * Initializes the repository and performs any setup needed
   * before health data can be fetched or observed.
   */
  suspend fun init()

  /**
   * Fetches all health data for the given [HealthCategoryId].
   */
  suspend fun fetch(categoryId: HealthCategoryId)

  /**
   * Observes health data updates for the given [HealthCategoryId].
   *
   * @return A [Flow] that emits the current list of [HealthData] and updates whenever data changes.
   */
  fun observe(categoryId: HealthCategoryId): Flow<List<HealthData>>
}
