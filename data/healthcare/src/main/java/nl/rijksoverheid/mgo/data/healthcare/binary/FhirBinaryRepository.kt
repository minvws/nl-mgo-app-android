package nl.rijksoverheid.mgo.data.healthcare.binary

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Repository that handles downloading FHIR (https://www.hl7.org/fhir/) binaries.
 */
interface FhirBinaryRepository {
    /**
     * Downloads a binary from the server.
     *
     * @param resourceEndpoint The document service resource endpoint from the [MgoOrganization].
     * @param fhirBinary The path to the binary on the FHIR server.
     * @return [Result] that if successful, contains [FhirBinary] that has the content type and the file downloaded on disk.
     */
    suspend fun download(
        resourceEndpoint: String,
        fhirBinary: String,
    ): Result<FhirBinary>

    /**
     * Removes all downloaded binaries on disk.
     */
    suspend fun cleanup()
}
