package nl.rijksoverheid.mgo.data.localisation

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.localisation.api.SearchApi
import nl.rijksoverheid.mgo.data.localisation.api.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.toHealthCareProviders

internal class DefaultHealthCareProviderRepository(private val searchApi: SearchApi) : HealthCareProviderRepository {
    override suspend fun search(
        name: String,
        city: String,
    ): Result<List<HealthCareProvider>> {
        val requestBody = SearchRequestBody(name = name, city = city)
        val result = executeNetworkRequest { searchApi.search(requestBody) }
        return result
            .mapCatching { response -> response.toHealthCareProviders() }
    }

    override suspend fun get(): Result<List<HealthCareProvider>> {
        TODO("Not yet implemented")
    }

    override suspend fun save(provider: HealthCareProvider) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(provider: HealthCareProvider) {
        TODO("Not yet implemented")
    }
}
