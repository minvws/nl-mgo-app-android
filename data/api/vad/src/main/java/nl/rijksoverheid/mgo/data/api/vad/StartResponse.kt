package nl.rijksoverheid.mgo.data.api.vad

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Json returned from [VadApi] to start the authentication from browser.
 */
@Serializable
data class StartResponse(
  @SerialName("authz_url") val authUrl: String,
)
