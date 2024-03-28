package nl.rijksoverheid.mgo.data.config

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConfigResponse(
    val androidMinimumVersion: Int,
    val configTTL: Long,
    val configMinimumIntervalSeconds: Long,
)
