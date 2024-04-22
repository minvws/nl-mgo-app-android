package nl.rijksoverheid.mgo.data.localisation

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.localisation.api.SearchApi
import nl.rijksoverheid.mgo.data.localisation.api.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.SearchResult
import nl.rijksoverheid.mgo.data.localisation.models.toSearchResults

internal class DefaultSearchRepository(private val searchApi: SearchApi) : SearchRepository {
    override suspend fun search(
        name: String,
        city: String,
    ): Result<List<SearchResult>> {
        val requestBody = SearchRequestBody(name = name, city = city)
        val result = executeNetworkRequest { searchApi.search(requestBody) }
        return result
            .mapCatching { response -> response.toSearchResults() }
    }
}
