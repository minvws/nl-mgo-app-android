package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.shared.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema

class TestUiSchemaMapper : UiSchemaMapper {
    private var summary: UISchema = TEST_UI_SCHEMA
    private var detail: UISchema = TEST_UI_SCHEMA

    fun setSummary(uiSchema: UISchema) {
        this.summary = uiSchema
    }

    fun setDetail(uiSchema: UISchema) {
        this.detail = uiSchema
    }

    override suspend fun getSummary(mgoResource: MgoResource): UISchema {
        return summary
    }

    override suspend fun getDetail(mgoResource: MgoResource): UISchema {
        return detail
    }
}
