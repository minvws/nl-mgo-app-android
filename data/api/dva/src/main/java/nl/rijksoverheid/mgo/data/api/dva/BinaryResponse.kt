package nl.rijksoverheid.mgo.data.api.dva

import kotlinx.serialization.Serializable

@Serializable
data class BinaryResponse(
    val id: String,
    val contentType: String,
    val content: String,
)
