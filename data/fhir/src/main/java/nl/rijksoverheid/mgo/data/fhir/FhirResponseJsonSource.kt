package nl.rijksoverheid.mgo.data.fhir

import java.io.File

sealed class FhirResponseJsonSource {
  data class Disk(
    val json: File,
  ) : FhirResponseJsonSource()

  data class Memory(
    val json: String,
  ) : FhirResponseJsonSource()
}

fun FhirResponseJsonSource.getJsonString() =
  when (this) {
    is FhirResponseJsonSource.Disk -> this.json.readText()
    is FhirResponseJsonSource.Memory -> this.json
  }
