package nl.rijksoverheid.mgo.data.config.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigResponse(
    val androidMinimumVersion: Int,
)
