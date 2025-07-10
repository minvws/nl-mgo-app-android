package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema

class TestUiSchemaMapper : UiSchemaMapper {
  private var summary: HealthUiSchema = TEST_UI_SCHEMA
  private var detail: HealthUiSchema = TEST_UI_SCHEMA
  private var detailError: Throwable? = null

  fun setSummary(uiSchema: HealthUiSchema) {
    this.summary = uiSchema
  }

  fun setDetail(uiSchema: HealthUiSchema) {
    this.detail = uiSchema
  }

  fun setDetailError(error: Throwable) {
    this.detailError = error
  }

  override suspend fun getSummary(mgoResource: MgoResource): HealthUiSchema = summary

  override suspend fun getDetail(mgoResource: MgoResource): HealthUiSchema {
    detailError?.let { error -> throw error }
    return detail
  }
}
