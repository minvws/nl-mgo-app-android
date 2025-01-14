package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema

interface UiSchemaRepository {
    suspend fun getSummary(mgoResource: MgoResourceJson): UISchema

    suspend fun getDetail(mgoResource: MgoResourceJson): UISchema
}
