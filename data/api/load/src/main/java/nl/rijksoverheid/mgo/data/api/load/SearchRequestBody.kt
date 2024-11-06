package nl.rijksoverheid.mgo.data.api.load

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequestBody(
    val name: String,
    val city: String,
)
