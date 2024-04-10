package nl.rijksoverheid.mgo.data.search.models

import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse

data class SearchResult(
    val id: String,
    val name: String,
    val city: String?,
    val address: String?,
    val postalCode: String?,
)

internal fun SearchResponse.toSearchResults(): List<SearchResult> {
    return organisations.mapNotNull { organisation ->
        val name = organisation.displayName ?: return@mapNotNull null
        val address = organisation.addresses.firstOrNull() ?: return@mapNotNull null
        SearchResult(
            id = organisation.id,
            name = name,
            city = address.city,
            address = address.address,
            postalCode = address.postalCode,
        )
    }
}
