package nl.rijksoverheid.mgo.data.healthcare.binary

import java.io.File

data class HealthCareBinary(
    val file: File,
    val contentType: String,
)
