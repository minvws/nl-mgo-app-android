package nl.rijksoverheid.mgo.data.fhir

typealias FhirResponseJson = String

interface FhirResponseJsonStore {
  suspend fun get(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
  ): FhirResponseJsonSource?

  suspend fun store(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
    json: String,
  ): FhirResponseJsonSource
}
