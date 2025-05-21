package nl.rijksoverheid.mgo.data.healthcare.binary

import java.io.File

/**
 * Represents a FHIR (https://www.hl7.org/fhir/) binary downloaded on disk.
 *
 * @param file The file downloaded on disk.
 * @param contentType The content type of the file.
 */
data class FhirBinary(
  val file: File,
  val contentType: String,
)

val TEST_FHIR_BINARY =
  FhirBinary(
    file = File(""),
    contentType = "application/pdf",
  )
