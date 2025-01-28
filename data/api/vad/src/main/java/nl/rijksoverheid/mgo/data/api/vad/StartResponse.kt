package nl.rijksoverheid.mgo.data.api.vad

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartResponse(
    @SerialName("authz_url") val authUrl: String,
)
