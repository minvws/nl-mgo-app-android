package nl.rijksoverheid.mgo.data.config.api

import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    val androidMinimumVersion: Int,
)
