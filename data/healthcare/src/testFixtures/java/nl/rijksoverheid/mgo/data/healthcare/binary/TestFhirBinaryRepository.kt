package nl.rijksoverheid.mgo.data.healthcare.binary

import kotlinx.coroutines.delay
import nl.rijksoverheid.mgo.data.healthcare.models.FhirBinary

class TestFhirBinaryRepository : FhirBinaryRepository {
  private var downloadResult: Result<FhirBinary>? = null
  private var downloads: Int = 0

  fun setDownloadResult(downloadResult: Result<FhirBinary>) {
    this.downloadResult = downloadResult
  }

  fun reset() {
    this.downloadResult = null
    downloads = 0
  }

  override suspend fun download(
    resourceEndpoint: String,
    fhirBinary: String,
  ): Result<FhirBinary> {
    val downloadResult = checkNotNull(downloadResult) { "You need to call setDownloadResult first" }
    delay(100)
    downloads++
    return downloadResult
  }
}
