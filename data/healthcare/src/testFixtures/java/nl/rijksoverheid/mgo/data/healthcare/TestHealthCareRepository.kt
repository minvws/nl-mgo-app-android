package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

class TestHealthCareRepository : HealthCareRepository {
    override suspend fun getUiSchema(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): List<Result<List<UISchema>>> {
        return listOf()
    }
}
