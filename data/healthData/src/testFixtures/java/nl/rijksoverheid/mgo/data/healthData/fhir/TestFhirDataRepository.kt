package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import java.io.File

class TestFhirDataRepository : FhirDataRepository {
  private var result: Result<File> = Result.failure(IllegalStateException("No result set"))

  fun setFetchResult(result: Result<File>) {
    this.result = result
  }

  override suspend fun fetch(
    endpoint: DataSetConfig.Endpoint,
    fhirVersion: String,
  ): Result<File> = result
}
