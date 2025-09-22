package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData

interface HealthDataRepository {
  suspend fun fetch(categoryId: HealthCategoryId)

  fun observe(categoryId: HealthCategoryId): Flow<List<HealthData>>
}
