package nl.rijksoverheid.mgo.data.healthcare.binary

interface FhirBinaryRepository {
    suspend fun download(
        resourceEndpoint: String,
        fhirBinary: String,
    ): Result<FhirBinary>

    suspend fun cleanup()
}
