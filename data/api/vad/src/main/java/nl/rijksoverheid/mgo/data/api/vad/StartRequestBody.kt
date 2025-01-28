package nl.rijksoverheid.mgo.data.api.vad

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartRequestBody(
    @SerialName("client_callback_url") val clientCallbackUrl: String,
)
