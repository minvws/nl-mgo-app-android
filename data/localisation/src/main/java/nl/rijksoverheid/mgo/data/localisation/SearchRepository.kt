package nl.rijksoverheid.mgo.data.localisation

import nl.rijksoverheid.mgo.data.localisation.models.SearchResult

interface SearchRepository {
    suspend fun search(
        name: String,
        city: String,
    ): Result<List<SearchResult>>
}
