package nl.rijksoverheid.mgo.data.config.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConfigResponse(
    val androidMinimumVersion: Int,
    val configTTL: Long,
    val configMinimumIntervalSeconds: Long,
)
