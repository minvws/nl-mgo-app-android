package nl.rijksoverheid.mgo.data.localisation.models

import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse

data class SearchResult(
    val id: String,
    val name: String,
    val city: String?,
    val address: String?,
    val postalCode: String?,
)

internal fun SearchResponse.toSearchResults(): List<SearchResult> {
    return organizations.mapNotNull { organization ->
        val name = organization.displayName ?: return@mapNotNull null
        val address = organization.addresses.firstOrNull() ?: return@mapNotNull null
        SearchResult(
            id = organization.id,
            name = name,
            city = address.city,
            address = address.address,
            postalCode = address.postalCode,
        )
    }
}
