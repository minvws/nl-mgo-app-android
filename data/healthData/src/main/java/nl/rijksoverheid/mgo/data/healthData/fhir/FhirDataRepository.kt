package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import java.io.File

interface FhirDataRepository {
  suspend fun fetch(
    resourceEndpoint: String,
    endpoint: DataSetConfig.Endpoint,
    fhirVersion: String,
  ): Result<File>
}
