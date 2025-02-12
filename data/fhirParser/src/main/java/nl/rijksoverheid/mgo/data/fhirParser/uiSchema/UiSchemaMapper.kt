package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.UiSchema

interface UiSchemaMapper {
    suspend fun getSummary(mgoResource: MgoResource): UiSchema

    suspend fun getDetail(mgoResource: MgoResource): UiSchema
}
