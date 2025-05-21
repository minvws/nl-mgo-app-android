package nl.rijksoverheid.mgo.data.api.load

import kotlinx.serialization.Serializable

/**
 * Json sent to [LoadApi] to search for health care providers.
 */
@Serializable
data class SearchRequestBody(
  val name: String,
  val city: String,
)
