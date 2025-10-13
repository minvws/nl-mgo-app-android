package nl.rijksoverheid.mgo.data.fhir

class MemoryFhirResponseJsonStore : FhirResponseJsonStore {
  data class MemoryFhirResponseJsonKey(
    val organizationId: String,
    val dataServiceId: String,
    val endpointId: String,
  )

  private val cachedFhirResponses = mutableMapOf<MemoryFhirResponseJsonKey, FhirResponseJson>()

  override suspend fun get(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
  ): FhirResponseJsonSource? {
    val key = MemoryFhirResponseJsonKey(organizationId = organizationId, dataServiceId = dataServiceId, endpointId = endpointId)
    return cachedFhirResponses[key]?.let { FhirResponseJsonSource.Memory(it) }
  }

  override suspend fun store(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
    json: String,
  ): FhirResponseJsonSource {
    val key = MemoryFhirResponseJsonKey(organizationId = organizationId, dataServiceId = dataServiceId, endpointId = endpointId)
    cachedFhirResponses[key] = json
    return FhirResponseJsonSource.Memory(json)
  }

  override suspend fun delete(organizationId: String) {
    val keyToRemove = cachedFhirResponses.keys.find { it.organizationId == organizationId }
    cachedFhirResponses.remove(keyToRemove)
  }
}
