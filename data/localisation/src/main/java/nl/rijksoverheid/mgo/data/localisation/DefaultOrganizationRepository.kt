package nl.rijksoverheid.mgo.data.localisation

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.load.LoadApi
import nl.rijksoverheid.mgo.data.api.load.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizations
import nl.rijksoverheid.mgo.data.localisation.models.toMgoOrganization
import nl.rijksoverheid.mgo.framework.storage.file.FileStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

internal class DefaultOrganizationRepository(
    private val loadApi: LoadApi,
    private val fileStore: FileStore,
) :
    OrganizationRepository {
    private val fileName = "organizations.json"

    override val storedOrganizationsFlow: MutableStateFlow<List<MgoOrganization>> = MutableStateFlow(runBlocking { get() })

    override suspend fun search(
        name: String,
        city: String,
    ): Flow<List<MgoOrganization>> {
        val requestBody =
            SearchRequestBody(name = name, city = city)
        val searchResponseFlow =
            flow {
                val result = executeNetworkRequest { loadApi.search(requestBody) }
                emit(result.getOrThrow())
            }
        return combine(searchResponseFlow, storedOrganizationsFlow) { searchResponse, storedOrganizations ->
            searchResponse.organizations.map { organization ->
                organization.toMgoOrganization(added = storedOrganizations.any { provider -> provider.id == organization.id })
            }
        }
    }

    override suspend fun get(): List<MgoOrganization> {
        val localMgoOrganizations = fileStore.getFile(MgoOrganizations::class.java, fileName)
        return localMgoOrganizations?.providers ?: listOf()
    }

    override suspend fun save(provider: MgoOrganization) {
        // Get stored health care providers
        val storedMgoOrganizations = fileStore.getFile(MgoOrganizations::class.java, fileName) ?: MgoOrganizations(listOf())

        // Add our provider we want to save
        val newProviders = storedMgoOrganizations.providers.toMutableList()
        newProviders.add(provider)
        val newStoredOrganizations = storedMgoOrganizations.copy(providers = newProviders)

        // Save new file
        fileStore.saveFile(file = newStoredOrganizations, name = fileName)

        // Update flow
        storedOrganizationsFlow.value = newStoredOrganizations.providers
    }

    override suspend fun delete(providerId: String) {
        // Get stored health care providers
        val storedMgoOrganizations = requireNotNull(fileStore.getFile(MgoOrganizations::class.java, fileName))

        // Delete the provider from the file
        val newProviders = storedMgoOrganizations.providers.toMutableList()
        newProviders.removeIf { provider -> provider.id == providerId }
        val newStoredOrganizations = storedMgoOrganizations.copy(providers = newProviders)

        // Save new file
        fileStore.saveFile(file = newStoredOrganizations, name = fileName)

        // Update flow
        storedOrganizationsFlow.value = newStoredOrganizations.providers
    }
}
