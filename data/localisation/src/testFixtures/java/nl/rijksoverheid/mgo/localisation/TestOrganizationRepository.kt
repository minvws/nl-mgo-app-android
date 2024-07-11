package nl.rijksoverheid.mgo.localisation

import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class TestOrganizationRepository : OrganizationRepository {
    override val storedHealthCareProvidersFlow: MutableStateFlow<List<MgoOrganization>> = MutableStateFlow(listOf())

    private var searchResults: List<MgoOrganization> = listOf()
    private var searchResultError: Throwable? = null

    override suspend fun search(
        name: String,
        city: String,
    ): Flow<List<MgoOrganization>> {
        return flow {
            searchResultError?.let { throwable -> throw throwable }
            emit(searchResults)
        }
    }

    fun setSearchResults(searchResults: List<MgoOrganization>) {
        this.searchResults = searchResults
    }

    fun setSearchResultsError(throwable: Throwable) {
        this.searchResultError = throwable
    }

    fun resetSearchResults() {
        this.searchResults = listOf()
    }

    fun setStoredProviders(providers: List<MgoOrganization>) {
        this.storedHealthCareProvidersFlow.value = providers
    }

    override suspend fun get(): List<MgoOrganization> {
        return storedHealthCareProvidersFlow.value
    }

    override suspend fun save(provider: MgoOrganization) {
        val newProviders = storedHealthCareProvidersFlow.value.toMutableList()
        newProviders.add(provider)
        storedHealthCareProvidersFlow.value = newProviders
    }

    override suspend fun delete(providerId: String) {
        val newProviders = storedHealthCareProvidersFlow.value.toMutableList()
        newProviders.removeIf { provider -> provider.id == providerId }
        storedHealthCareProvidersFlow.value = newProviders
    }
}
