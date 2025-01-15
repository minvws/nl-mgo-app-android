package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.shared.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema

class TestUiSchemaMapper : UiSchemaMapper {
    override suspend fun getSummary(mgoResource: MgoResource): UISchema {
        return TEST_UI_SCHEMA
    }

    override suspend fun getDetail(mgoResource: MgoResource): UISchema {
        return TEST_UI_SCHEMA
    }
}
