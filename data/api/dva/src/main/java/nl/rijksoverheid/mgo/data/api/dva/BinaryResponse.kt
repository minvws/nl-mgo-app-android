package nl.rijksoverheid.mgo.data.api.dva

import kotlinx.serialization.Serializable

/**
 * Json returned from [DvaApi] when fetching the contents of a binary.
 */
@Serializable
data class BinaryResponse(
  val id: String,
  val contentType: String,
  val content: String,
)
