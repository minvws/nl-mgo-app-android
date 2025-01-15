package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema

interface UiSchemaMapper {
    suspend fun getSummary(mgoResource: MgoResource): UISchema

    suspend fun getDetail(mgoResource: MgoResource): UISchema
}
