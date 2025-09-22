package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import okhttp3.ResponseBody

class TestFhirDataRepository : FhirDataRepository {
  private var result: Result<ResponseBody> = Result.failure(IllegalStateException("No result set"))

  fun setFetchResult(result: Result<ResponseBody>) {
    this.result = result
  }

  override suspend fun fetch(
    resourceEndpoint: String,
    endpoint: DataSetConfig.Endpoint,
    fhirVersion: String,
  ): Result<ResponseBody> = result
}
