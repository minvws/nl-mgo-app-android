package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Named

class DefaultFhirDataRepository
  @Inject
  constructor(
    private val dvaApi: DvaApi,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
  ) : FhirDataRepository {
    override suspend fun fetch(
      resourceEndpoint: String,
      endpoint: DataSetConfig.Endpoint,
      fhirVersion: String,
    ): Result<ResponseBody> =
      executeNetworkRequest {
        dvaApi.get(
          resourceEndpoint = resourceEndpoint,
          url = "$dvaApiBaseUrl/fhir${endpoint.url}",
          accept = "application/fhir+json; fhirVersion=$fhirVersion",
        )
      }
  }
