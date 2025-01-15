package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.fhirParser.UISchema
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestUiSchemaRepository : UiSchemaRepository {
    private val uiSchemaResult = mutableListOf<Result<List<nl.rijksoverheid.mgo.data.fhirParser.UISchema>>>()

    fun setUiSchemaResult(result: List<Result<List<nl.rijksoverheid.mgo.data.fhirParser.UISchema>>>) {
        uiSchemaResult.clear()
        uiSchemaResult.addAll(result)
    }

    override suspend fun getUiSchema(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): List<Result<List<nl.rijksoverheid.mgo.data.fhirParser.UISchema>>> {
        return uiSchemaResult
    }
}
