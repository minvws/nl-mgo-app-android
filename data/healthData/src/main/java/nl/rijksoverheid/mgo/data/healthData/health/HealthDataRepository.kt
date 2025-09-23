package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryGroupConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Repository for accessing and observing health data.
 */
interface HealthDataRepository {
  /**
   * Fetches health data for a [HealthCategoryGroupConfig.HealthCategory] and [MgoOrganization].
   *
   * @param category The [HealthCategoryGroupConfig.HealthCategory] to fetch the health data for.
   * @param organization The [MgoOrganization] to fetch the health data for.
   */
  suspend fun fetch(
    category: HealthCategoryGroupConfig.HealthCategory,
    organization: MgoOrganization,
  )

  /**
   * Observes health data updates for the given [HealthCategoryId].
   *
   * @param category The [HealthCategoryGroupConfig.HealthCategory] to observe health data for.
   * @return A [Flow] that emits the current list of [HealthData] and updates whenever data changes.
   */
  fun observe(category: HealthCategoryGroupConfig.HealthCategory): Flow<List<HealthData>>
}
