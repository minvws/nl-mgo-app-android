package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named

class DefaultFhirDataRepository
  @Inject
  constructor(
    private val dvaApi: DvaApi,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    private val cacheFileStore: CacheFileStore,
    private val base64Util: Base64Util,
  ) : FhirDataRepository {
    override suspend fun fetch(
      endpoint: DataSetConfig.Endpoint,
      fhirVersion: String,
    ): Result<File> {
      val result =
        executeNetworkRequest {
          dvaApi.get(
            resourceEndpoint = endpoint.url,
            url = dvaApiBaseUrl,
            accept = "application/fhir+json; fhirVersion=$fhirVersion",
          )
        }

      return result.mapCatching { responseBody ->
        val responseJson = responseBody.string()
        val file =
          cacheFileStore.saveFile(
            name = UUID.randomUUID().toString(),
            contentType = "application/json",
            base64Util.encode(responseJson),
          )
        file
      }
    }
  }
