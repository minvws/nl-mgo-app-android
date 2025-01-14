package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema

interface UiSchemaRepository {
    suspend fun getSummary(
        mgoResources: List<MgoResourceJson>,
        profiles: List<String>,
    ): List<UISchema>

    suspend fun getDetail(
        mgoResources: List<MgoResourceJson>,
        profiles: List<String>,
    ): List<UISchema>
}
