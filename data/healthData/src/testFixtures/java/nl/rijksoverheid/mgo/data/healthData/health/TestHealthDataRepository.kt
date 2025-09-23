package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryGroupConfig
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData
import nl.rijksoverheid.mgo.data.healthData.health.models.TEST_HEALTH_DATA_LOADING
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestHealthDataRepository : HealthDataRepository {
  private var fetchCalledAmount: Int = 0

  fun getFetchCalledAmount() = fetchCalledAmount

  override suspend fun fetch(
    category: HealthCategoryGroupConfig.HealthCategory,
    organization: MgoOrganization,
  ) {
    fetchCalledAmount++
  }

  override fun observe(category: HealthCategoryGroupConfig.HealthCategory): Flow<List<HealthData>> = flowOf(listOf(TEST_HEALTH_DATA_LOADING))
}
