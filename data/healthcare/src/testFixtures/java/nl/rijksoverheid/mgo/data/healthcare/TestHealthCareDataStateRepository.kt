package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHealthCareDataStateRepository : HealthCareDataStateRepository {
    override fun get(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): Flow<HealthCareDataState> {
        return flow {
            emit(
                HealthCareDataState(
                    loading = false,
                    organization = organization,
                    category = category,
                    uiSchemaListResults = listOf(Result.success(listOf(TEST_UI_SCHEMA_MEDICATION))),
                ),
            )
        }
    }
}
