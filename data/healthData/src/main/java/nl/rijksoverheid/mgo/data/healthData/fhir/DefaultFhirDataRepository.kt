package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class DefaultFhirDataRepository
  @Inject
  constructor(
    private val dvaApi: DvaApi,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    private val cacheFileStore: CacheFileStore,
  ) : FhirDataRepository {
    override suspend fun fetch(
      resourceEndpoint: String,
      endpoint: DataSetConfig.Endpoint,
      fhirVersion: String,
    ): Result<File> {
      val result =
        executeNetworkRequest {
          dvaApi.get(
            resourceEndpoint = resourceEndpoint,
            url = "$dvaApiBaseUrl/fhir${endpoint.url}",
            accept = "application/fhir+json; fhirVersion=$fhirVersion",
          )
        }

      return result.mapCatching { responseBody ->
        val responseJson = responseBody.string()
        val file =
          cacheFileStore.saveFile(
            name = endpoint.id,
            contentType = "application/json",
            responseJson.toByteArray(Charsets.UTF_8),
          )
        file
      }
    }
  }
