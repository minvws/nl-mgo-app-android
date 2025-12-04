package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

interface FhirRepository {
  fun observe(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
  ): Flow<FhirResponse>

  fun observe(): Flow<List<FhirResponse>>

  suspend fun fetch(
    organizationId: String,
    medmijId: String?,
    dataServiceId: String,
    endpointId: String,
    resourceEndpoint: String,
    fhirVersion: FhirVersion,
    url: String,
    forceRefresh: Boolean,
  )

  suspend fun delete(organizationId: String)

  suspend fun fetchBinary(
    resourceEndpoint: String,
    url: String,
  ): Result<FhirBinary>
}
