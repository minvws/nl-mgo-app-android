package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION

class TestInitHealthDataFetching : InitHealthDataFetching {
  override suspend fun invoke(): Flow<List<MgoOrganization>> = flowOf(listOf(TEST_MGO_ORGANIZATION))
}
