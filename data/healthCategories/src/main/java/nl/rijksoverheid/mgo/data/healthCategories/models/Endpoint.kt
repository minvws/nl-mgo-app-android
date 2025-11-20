package nl.rijksoverheid.mgo.data.healthCategories.models

import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

data class Endpoint(
  val dataServiceId: String,
  val endpointId: String,
  val endpointPath: String,
  val resourceEndpoint: String,
  val fhirVersion: FhirVersion,
)
