package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import okhttp3.ResponseBody

interface FhirDataRepository {
  suspend fun fetch(
    resourceEndpoint: String,
    endpoint: DataSetConfig.Endpoint,
    fhirVersion: String,
  ): Result<ResponseBody>
}
