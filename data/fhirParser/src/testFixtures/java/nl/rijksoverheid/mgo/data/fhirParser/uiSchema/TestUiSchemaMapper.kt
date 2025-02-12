package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.UiSchema
import nl.rijksoverheid.mgo.data.fhirParser.shared.TEST_UI_SCHEMA

class TestUiSchemaMapper : UiSchemaMapper {
    private var summary: UiSchema = TEST_UI_SCHEMA
    private var detail: UiSchema = TEST_UI_SCHEMA

    fun setSummary(uiSchema: UiSchema) {
        this.summary = uiSchema
    }

    fun setDetail(uiSchema: UiSchema) {
        this.detail = uiSchema
    }

    override suspend fun getSummary(mgoResource: MgoResource): UiSchema {
        return summary
    }

    override suspend fun getDetail(mgoResource: MgoResource): UiSchema {
        return detail
    }
}
