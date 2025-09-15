package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema

/**
 * Creates [HealthUiSchema] based on a [MgoResource].
 */
interface UiSchemaMapper {
  /**
   * Retrieves a summarized version of the most important healthcare data from an [MgoResource].
   *
   * @param healthCareOrganizationName The name of the health care organization.
   * @param mgoResource The [MgoResource] created in [MgoResourceMapper].
   * @return [HealthUiSchema].
   */
  suspend fun getSummary(
    healthCareOrganizationName: String,
    mgoResource: MgoResource,
  ): HealthUiSchema

  /**
   * Retrieves the complete set of healthcare data from an [MgoResource].
   *
   * @param healthCareOrganizationName The name of the health care organization.
   * @param mgoResource The [MgoResource] created in [MgoResourceMapper].
   * @return [HealthUiSchema].
   */
  suspend fun getDetail(
    healthCareOrganizationName: String,
    mgoResource: MgoResource,
  ): HealthUiSchema
}
