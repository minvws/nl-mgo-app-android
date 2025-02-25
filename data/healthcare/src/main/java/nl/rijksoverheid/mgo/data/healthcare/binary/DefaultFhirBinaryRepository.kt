package nl.rijksoverheid.mgo.data.healthcare.binary

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import javax.inject.Inject

/**
 * Repository that handles downloading FHIR (https://www.hl7.org/fhir/) binaries.
 *
 * @param cacheFileStore Store that handles file in cache.
 * @param dvaApi API interface for fetching FHIR resources from FHIR Server (https://www.hl7.org/fhir/).
 */
internal class DefaultFhirBinaryRepository
    @Inject
    constructor(
        private val cacheFileStore: CacheFileStore,
        private val dvaApi: DvaApi,
    ) : FhirBinaryRepository {
        /**
         * Downloads a binary from the server.
         *
         * @param resourceEndpoint The document service resource endpoint from the [MgoOrganization].
         * @param fhirBinary The path to the binary on the FHIR server.
         * @return [Result] that if successful, contains [FhirBinary] that has the content type and the file downloaded on disk.
         */
        override suspend fun download(
            resourceEndpoint: String,
            fhirBinary: String,
        ): Result<FhirBinary> {
            val response =
                executeNetworkRequest {
                    dvaApi.binary(
                        resourceEndpoint = resourceEndpoint,
                        fhirBinary = fhirBinary,
                    )
                }
            return response
                .mapCatching { binaryResponse ->
                    val file =
                        cacheFileStore.saveFile(
                            name = binaryResponse.id,
                            contentType = binaryResponse.contentType,
                            base64Content = binaryResponse.content,
                        )
                    FhirBinary(
                        file = file,
                        contentType = binaryResponse.contentType,
                    )
                }
        }

        /**
         * Removes all downloaded binaries on disk.
         */
        override suspend fun cleanup() {
            cacheFileStore.deleteAll()
        }
    }
