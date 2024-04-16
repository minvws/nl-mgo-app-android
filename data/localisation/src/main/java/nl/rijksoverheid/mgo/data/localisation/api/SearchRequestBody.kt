package nl.rijksoverheid.mgo.data.localisation.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SearchRequestBody(
    val name: String,
    val city: String,
)
