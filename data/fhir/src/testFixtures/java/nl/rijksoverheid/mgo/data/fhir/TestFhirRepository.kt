package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class TestFhirRepository : FhirRepository {
  private var observeResults: List<FhirResponse> = listOf(TEST_FHIR_RESPONSE_SUCCESS(false))
  private var observeResult: FhirResponse = TEST_FHIR_RESPONSE_SUCCESS(false)
  private var fetchBinaryResult: Result<FhirBinary> = Result.failure(IllegalStateException("Not set"))
  private var fetchAmount: Int = 0

  fun setObserveResult(response: FhirResponse) {
    this.observeResult = response
  }

  fun setObserveResults(responses: List<FhirResponse>) {
    this.observeResults = responses
  }

  override fun observe(request: FhirRequest): Flow<FhirResponse> =
    flow {
      emit(observeResult)
    }

  override fun observe(): Flow<List<FhirResponse>> = flow { emit(observeResults) }

  override suspend fun fetch(
    request: FhirRequest,
    forceRefresh: Boolean,
  ) {
    fetchAmount++
  }

  override suspend fun retry(requests: List<FhirRequest>) {
  }

  override suspend fun delete(organizationId: String) {
  }

  override suspend fun fetchBinary(
    resourceEndpoint: String,
    url: String,
    fileDir: File,
  ): Result<FhirBinary> = this.fetchBinaryResult

  fun setFetchBinaryResult(result: Result<FhirBinary>) {
    this.fetchBinaryResult = result
  }
}
