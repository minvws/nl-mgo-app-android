package nl.rijksoverheid.mgo.data.healthData.fhir.models

import java.io.File

sealed class FhirResponseState {
  data object Loading : FhirResponseState()

  data class Success(
    val fhirResponse: File,
  ) : FhirResponseState()

  data class Error(
    val throwable: Throwable?,
  ) : FhirResponseState()
}
