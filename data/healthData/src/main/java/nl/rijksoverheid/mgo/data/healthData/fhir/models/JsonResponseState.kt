package nl.rijksoverheid.mgo.data.healthData.fhir.models

import java.io.File

sealed class JsonResponseState {
  data object Loading : JsonResponseState()

  data class Success(
    val file: File,
  ) : JsonResponseState()

  data class Error(
    val throwable: Throwable?,
  ) : JsonResponseState()
}
