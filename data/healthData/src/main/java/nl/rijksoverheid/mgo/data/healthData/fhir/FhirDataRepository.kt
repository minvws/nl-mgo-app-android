package nl.rijksoverheid.mgo.data.healthData.fhir

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.data.healthData.fhir.models.JsonResponseState

interface FhirDataRepository {
  suspend fun fetch(
    endpoint: DataSetConfig.Endpoint,
    fhirVersion: String,
  ): Flow<JsonResponseState>
}
