package nl.rijksoverheid.mgo.data.localisation

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class TestHealthCareProviderRepository : HealthCareProviderRepository {
    override val storedHealthCareProvidersFlow: MutableStateFlow<List<HealthCareProvider>>
        get() = MutableStateFlow(listOf())

    private var searchResults: List<HealthCareProvider> = listOf()
    private var searchResultError: Throwable? = null

    override suspend fun search(
        name: String,
        city: String,
    ): Flow<List<HealthCareProvider>> {
        return flow {
            searchResultError?.let { throwable -> throw throwable }
            emit(searchResults)
        }
    }

    fun setSearchResults(searchResults: List<HealthCareProvider>) {
        this.searchResults = searchResults
    }

    fun setSearchResultsError(throwable: Throwable) {
        this.searchResultError = throwable
    }

    fun resetSearchResults() {
        this.searchResults = listOf()
    }

    fun setStoredProviders(providers: List<HealthCareProvider>) {
        this.storedHealthCareProvidersFlow.value = providers
    }

    override suspend fun get(): List<HealthCareProvider> {
        return storedHealthCareProvidersFlow.value
    }

    override suspend fun save(provider: HealthCareProvider) {
        val newProviders = storedHealthCareProvidersFlow.value.toMutableList()
        newProviders.add(provider)
        storedHealthCareProvidersFlow.value = newProviders
    }

    override suspend fun delete(provider: HealthCareProvider) {
        val newProviders = storedHealthCareProvidersFlow.value.toMutableList()
        newProviders.remove(provider)
        storedHealthCareProvidersFlow.value = newProviders
    }
}
