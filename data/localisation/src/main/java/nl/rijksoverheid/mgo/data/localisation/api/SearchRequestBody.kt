package nl.rijksoverheid.mgo.data.localisation.api

internal data class SearchRequestBody(
    val name: String,
    val city: String,
)
