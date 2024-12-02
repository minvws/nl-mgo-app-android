package nl.rijksoverheid.mgo.data.healthcare.binary

class TestHealthCareBinaryRepository : HealthCareBinaryRepository {
    private var downloadResult: Result<HealthCareBinary>? = null
    private var downloads: Int = 0

    fun setDownloadResult(downloadResult: Result<HealthCareBinary>) {
        this.downloadResult = downloadResult
    }

    fun reset() {
        this.downloadResult = null
        downloads = 0
    }

    override suspend fun download(
        resourceEndpoint: String,
        fhirBinary: String,
    ): Result<HealthCareBinary> {
        val downloadResult = checkNotNull(downloadResult) { "You need to call setDownloadResult first" }
        downloads++
        return downloadResult
    }

    override suspend fun cleanup() {
        downloads = 0
    }
}
