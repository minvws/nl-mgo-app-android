package nl.rijksoverheid.mgo.data.api.load

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRequestBody(
    val name: String,
    val city: String,
)
