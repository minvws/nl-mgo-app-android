package nl.rijksoverheid.mgo.data.healthcare.binary

import java.io.File

data class FhirBinary(
    val file: File,
    val contentType: String,
)

val TEST_FHIR_BINARY =
    FhirBinary(
        file = File(""),
        contentType = "application/pdf",
    )
