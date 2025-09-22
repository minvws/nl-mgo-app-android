package nl.rijksoverheid.mgo.data.healthData.configuration.models

import androidx.annotation.VisibleForTesting
import kotlinx.serialization.Serializable

typealias DataSetConfigEndpointId = String

/**
 * Defines the configuration for retrieving health data from external endpoints.
 *
 * This model mirrors the JSON structure exchanged between clients (Android, iOS, Web),
 * ensuring consistent interpretation across platforms.
 *
 * @property id   Unique identifier of the data set configuration.
 * @property name Human-readable name of the configuration.
 * @property fhirVersion The FHIR version supported by the configured endpoints.
 * @property endpoints   Endpoints from which health data can be retrieved.
 */
@Serializable
data class DataSetConfig(
  val id: String,
  val name: String,
  val fhirVersion: String,
  val endpoints: List<Endpoint>,
) {
  /**
   * Describes a single endpoint within a data set configuration.
   *
   * @property id      Unique identifier of the endpoint.
   * @property url     Relative or absolute URL where the endpoint can be reached.
   * @property profiles Expected FHIR profiles present in the endpoint's response.
   */
  @Serializable
  data class Endpoint(
    val id: DataSetConfigEndpointId,
    val url: String,
    val profiles: List<HealthCategoryProfile>,
  )
}

val TEST_ENDPOINT =
  DataSetConfig.Endpoint(
    id = "1",
    url = "",
    profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Problem"),
  )

@VisibleForTesting
val TEST_COMMON_CLINICAL_DATA_SET =
  DataSetConfig(
    id = "48",
    name = "Common Clinical Dataset",
    fhirVersion = "R3",
    endpoints =
      listOf(
        DataSetConfig.Endpoint(
          id = "problem",
          url = "/Condition",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Problem"),
        ),
        DataSetConfig.Endpoint(
          id = "alert",
          url = "/Flag",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Alert"),
        ),
      ),
  )

@VisibleForTesting
val TEST_GENERAL_PRACTITIONER_DATA =
  DataSetConfig(
    id = "49",
    name = "General Practitioner Data",
    fhirVersion = "R3",
    endpoints =
      listOf(
        DataSetConfig.Endpoint(
          id = "episodes",
          url = "/EpisodeOfCare",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Alert", "http://fhir.nl/fhir/StructureDefinition/nl-core-episodeofcare"),
        ),
      ),
  )
