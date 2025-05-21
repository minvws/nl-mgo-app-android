package nl.rijksoverheid.mgo.localisation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestOrganizationRepository : OrganizationRepository {
  override val storedOrganizationsFlow: MutableStateFlow<List<MgoOrganization>> = MutableStateFlow(listOf())

  private var searchResults: List<MgoOrganization> = listOf()
  private var searchResultError: Throwable? = null

  override fun search(
    name: String,
    city: String,
  ): Flow<List<MgoOrganization>> {
    return flow {
      searchResultError?.let { throwable -> throw throwable }
      emit(searchResults)
    }
  }

  override suspend fun searchDemo(): Flow<List<MgoOrganization>> {
    return flow {
      searchResultError?.let { throwable -> throw throwable }
      emit(searchResults)
    }
  }

  suspend fun setSearchResults(searchResults: List<MgoOrganization>) {
    delay(100)
    this.searchResults = searchResults
  }

  fun setSearchResultsError(throwable: Throwable) {
    this.searchResultError = throwable
  }

  fun resetSearchResults() {
    this.searchResults = listOf()
  }

  fun setStoredProviders(providers: List<MgoOrganization>) {
    this.storedOrganizationsFlow.value = providers
  }

  fun assertNoProviders(): Boolean {
    return storedOrganizationsFlow.value.isEmpty()
  }

  override suspend fun get(): List<MgoOrganization> {
    return storedOrganizationsFlow.value
  }

  override suspend fun save(provider: MgoOrganization) {
    val newProviders = storedOrganizationsFlow.value.toMutableList()
    newProviders.add(provider)
    storedOrganizationsFlow.value = newProviders
  }

  override suspend fun delete(providerId: String) {
    val newProviders = storedOrganizationsFlow.value.toMutableList()
    newProviders.removeIf { provider -> provider.id == providerId }
    storedOrganizationsFlow.value = newProviders
  }

  override suspend fun deleteAll() {
    storedOrganizationsFlow.value = listOf()
  }
}
