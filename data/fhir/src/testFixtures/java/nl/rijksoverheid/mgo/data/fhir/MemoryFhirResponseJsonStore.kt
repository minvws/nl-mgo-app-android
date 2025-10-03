package nl.rijksoverheid.mgo.data.fhir

class MemoryFhirResponseJsonStore : FhirResponseJsonStore {
  data class MemoryFhirResponseJsonKey(
    val organizationId: String,
    val endpointId: String,
  )

  private val cachedFhirResponses = mutableMapOf<MemoryFhirResponseJsonKey, FhirResponseJson>()

  override suspend fun get(
    organizationId: String,
    endpointId: String,
  ): FhirResponseJsonSource? {
    val key = MemoryFhirResponseJsonKey(organizationId = organizationId, endpointId = endpointId)
    return cachedFhirResponses[key]?.let { FhirResponseJsonSource.Memory(it) }
  }

  override suspend fun store(
    organizationId: String,
    endpointId: String,
    json: String,
  ): FhirResponseJsonSource {
    val key = MemoryFhirResponseJsonKey(organizationId = organizationId, endpointId = endpointId)
    cachedFhirResponses[key] = json
    return FhirResponseJsonSource.Memory(json)
  }
}
