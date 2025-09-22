package nl.rijksoverheid.mgo.data.healthcare.binary

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.healthcare.models.FhirBinary
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import javax.inject.Inject

/**
 * Handles downloading FHIR (https://www.hl7.org/fhir/) binaries.
 *
 * @param cacheFileStore The [CacheFileStore] to save the binary in.
 * @param dvaApi The [DvaApi] to communicate with the server.
 * @param base64Util The [Base64Util] to decode the response from the server.
 */
internal class DefaultFhirBinaryRepository
  @Inject
  constructor(
    private val cacheFileStore: CacheFileStore,
    private val dvaApi: DvaApi,
    private val base64Util: Base64Util,
  ) : FhirBinaryRepository {
    /**
     * Downloads a binary from the server.
     *
     * @param resourceEndpoint The document service resource endpoint from the [MgoOrganization].
     * @param fhirBinary The path to the binary on the FHIR server.
     * @return [Result] that if successful, contains [FhirBinary] that has the content type and the downloaded file.
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
              content = base64Util.decode(binaryResponse.content),
            )
          FhirBinary(
            file = file,
            contentType = binaryResponse.contentType,
          )
        }
    }
  }
