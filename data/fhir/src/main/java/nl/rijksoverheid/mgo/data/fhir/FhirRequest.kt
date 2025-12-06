package nl.rijksoverheid.mgo.data.fhir

import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

data class FhirRequest(
  val organizationId: String,
  val medmijId: String?,
  val dataServiceId: String,
  val endpointId: String,
  val endpointPath: String,
  val resourceEndpoint: String,
  val fhirVersion: FhirVersion,
  val url: String,
)

val TEST_FHIR_REQUEST =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "1",
    endpointId = "1",
    endpointPath = "",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
    url = "",
  )
