package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow

interface FhirRepository {
  fun observe(request: FhirRequest): Flow<FhirResponse>

  fun observe(): Flow<List<FhirResponse>>

  suspend fun fetch(
    request: FhirRequest,
    forceRefresh: Boolean,
  )

  suspend fun retry(requests: List<FhirRequest>)

  suspend fun delete(organizationId: String)

  suspend fun fetchBinary(
    resourceEndpoint: String,
    url: String,
  ): Result<FhirBinary>
}
