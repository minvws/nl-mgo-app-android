package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema

class TestUiSchemaMapper : UiSchemaMapper {
  private var summary: HealthUiSchema = TEST_UI_SCHEMA
  private var detail: HealthUiSchema = TEST_UI_SCHEMA

  fun setSummary(uiSchema: HealthUiSchema) {
    this.summary = uiSchema
  }

  fun setDetail(uiSchema: HealthUiSchema) {
    this.detail = uiSchema
  }

  override suspend fun getSummary(mgoResource: MgoResource): HealthUiSchema {
    return summary
  }

  override suspend fun getDetail(mgoResource: MgoResource): HealthUiSchema {
    return detail
  }
}
