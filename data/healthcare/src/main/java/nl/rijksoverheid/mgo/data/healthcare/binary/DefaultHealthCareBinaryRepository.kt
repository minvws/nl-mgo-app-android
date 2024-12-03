package nl.rijksoverheid.mgo.data.healthcare.binary

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import javax.inject.Inject

internal class DefaultHealthCareBinaryRepository
    @Inject
    constructor(
        private val cacheFileStore: CacheFileStore,
        private val dvaApi: DvaApi,
    ) : HealthCareBinaryRepository {
        override suspend fun download(
            resourceEndpoint: String,
            fhirBinary: String,
        ): Result<HealthCareBinary> {
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
                    HealthCareBinary(
                        file = file,
                        contentType = binaryResponse.contentType,
                    )
                }
        }

        override suspend fun cleanup() {
            cacheFileStore.deleteAll()
        }
    }
