package nl.rijksoverheid.mgo.data.api.vad

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Json sent to [VadApi] to start authentication.
 */
@Serializable
data class StartRequestBody(
  @SerialName("client_callback_url") val clientCallbackUrl: String,
)
