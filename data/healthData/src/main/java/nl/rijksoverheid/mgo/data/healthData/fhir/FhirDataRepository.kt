package nl.rijksoverheid.mgo.data.healthData.fhir

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import java.io.File

interface FhirDataRepository {
  suspend fun fetch(
    endpoint: DataSetConfig.Endpoint,
    fhirVersion: String,
  ): Result<File>
}
