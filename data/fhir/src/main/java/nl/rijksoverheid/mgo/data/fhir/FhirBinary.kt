package nl.rijksoverheid.mgo.data.fhir

import java.io.File

data class FhirBinary(
  val file: File,
  val contentType: String,
)
