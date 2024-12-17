package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHealthCareDataStateRepository : HealthCareDataStateRepository {
    override fun get(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): Flow<HealthCareDataState> {
        return flow {
            emit(
                TEST_HEALTH_CARE_DATA_STATE_LOADED,
            )
        }
    }
}
