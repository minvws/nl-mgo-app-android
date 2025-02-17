package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema

interface UiSchemaMapper {
    suspend fun getSummary(mgoResource: MgoResource): HealthUiSchema

    suspend fun getDetail(mgoResource: MgoResource): HealthUiSchema
}
