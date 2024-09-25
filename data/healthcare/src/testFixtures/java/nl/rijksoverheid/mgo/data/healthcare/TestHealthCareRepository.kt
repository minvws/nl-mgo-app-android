package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

class TestHealthCareRepository : HealthCareRepository {
    private val uiSchemaResult = mutableListOf<Result<List<UISchema>>>()

    fun setUiSchemaResult(result: List<Result<List<UISchema>>>) {
        uiSchemaResult.clear()
        uiSchemaResult.addAll(result)
    }

    override suspend fun getUiSchema(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): List<Result<List<UISchema>>> {
        return uiSchemaResult
    }
}
