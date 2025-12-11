package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow

interface FhirRepository {
  fun observe(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
  ): Flow<FhirResponse>

  fun observe(): Flow<List<FhirResponse>>

  suspend fun fetch(
    request: FhirRequest,
    forceRefresh: Boolean,
  )

  suspend fun delete(organizationId: String)

  suspend fun deleteFailed()

  suspend fun fetchBinary(
    resourceEndpoint: String,
    url: String,
  ): Result<FhirBinary>
}
