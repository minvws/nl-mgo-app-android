package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

class TestFhirRepository : FhirRepository {
  private var observeResult: FhirResponse = TEST_FHIR_RESPONSE_SUCCESS(false)
  private var fetchBinaryResult: Result<FhirBinary> = Result.failure(IllegalStateException("Not set"))

  fun setObserveResult(response: FhirResponse) {
    this.observeResult = response
  }

  override fun observe(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
  ): Flow<FhirResponse> =
    flow {
      emit(observeResult)
    }

  override suspend fun fetch(
    organizationId: String,
    medmijId: String?,
    dataServiceId: String,
    endpointId: String,
    resourceEndpoint: String,
    fhirVersion: FhirVersion,
    url: String,
    forceRefresh: Boolean,
  ) {
  }

  override suspend fun delete(organizationId: String) {
  }

  override suspend fun fetchBinary(
    resourceEndpoint: String,
    url: String,
  ): Result<FhirBinary> = this.fetchBinaryResult

  fun setFetchBinaryResult(result: Result<FhirBinary>) {
    this.fetchBinaryResult = result
  }
}
