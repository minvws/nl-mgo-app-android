package nl.rijksoverheid.mgo.data.healthcare.binary

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

interface HealthCareBinaryRepository {
    /**
     * Downloads an attachment from the FHIR server.
     * @param resourceEndpoint The document service resource endpoint from the [MgoOrganization].
     * @param fhirBinary The path to the binary on the FHIR server.
     */
    suspend fun download(
        resourceEndpoint: String,
        fhirBinary: String,
    ): Result<HealthCareBinary>

    /**
     * Removes all cached binaries that were downloaded.
     */
    suspend fun cleanup()
}
