package nl.rijksoverheid.mgo.data.healthCategories.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

typealias DataSetId = String
typealias DataSetConfigEndpointId = String

@Serializable
data class DataSet(
  val id: DataSetId,
  val name: String,
  @SerialName("fhirVersionEnum") val fhirVersion: FhirVersion,
  val endpoints: List<Endpoint>,
) {
  @Serializable
  data class Endpoint(
    val id: DataSetConfigEndpointId,
    val path: String,
    val profiles: List<HealthCategoryProfile>,
  )
}
