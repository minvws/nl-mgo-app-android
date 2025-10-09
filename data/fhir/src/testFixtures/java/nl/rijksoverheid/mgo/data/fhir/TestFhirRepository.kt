package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

class TestFhirRepository : FhirRepository {
  private var fetchBinaryResult: Result<FhirBinary> = Result.failure(IllegalStateException("Not set"))

  override fun observe(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
  ): Flow<FhirResponse> =
    flow {
      emit(FhirResponse.Success(organizationId = "1", dataServiceId = "1", endpointId = "1", jsonSource = FhirResponseJsonSource.Memory(""), isEmpty = false))
    }

  override suspend fun fetch(
    organizationId: String,
    dataServiceId: String,
    endpointId: String,
    resourceEndpoint: String,
    fhirVersion: FhirVersion,
    url: String,
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
