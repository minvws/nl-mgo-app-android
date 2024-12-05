package nl.rijksoverheid.mgo.data.localisation

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.load.LoadApi
import nl.rijksoverheid.mgo.data.api.load.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizations
import nl.rijksoverheid.mgo.data.localisation.models.toMgoOrganization
import nl.rijksoverheid.mgo.framework.storage.file.EncryptedFileStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

internal class DefaultOrganizationRepository(
    private val loadApi: LoadApi,
    private val encryptedFileStore: EncryptedFileStore,
) :
    OrganizationRepository {
    private val fileName = "organizations.json"

    override val storedOrganizationsFlow: MutableStateFlow<List<MgoOrganization>> = MutableStateFlow(runBlocking { get() })

    override fun search(
        name: String,
        city: String,
    ): Flow<List<MgoOrganization>> {
        val requestBody =
            SearchRequestBody(name = name.trim(), city = city.trim())
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

    override suspend fun searchDemo(): Flow<List<MgoOrganization>> {
        val searchResponseFlow =
            flow {
                val result = executeNetworkRequest { loadApi.searchDemo() }
                emit(result.getOrThrow())
            }
        return combine(searchResponseFlow, storedOrganizationsFlow) { searchResponse, storedOrganizations ->
            searchResponse.organizations.map { organization ->
                organization.toMgoOrganization(added = storedOrganizations.any { provider -> provider.id == organization.id })
            }
        }
    }

    override suspend fun get(): List<MgoOrganization> {
        val localMgoOrganizations = encryptedFileStore.getFile(MgoOrganizations::class, fileName)
        return localMgoOrganizations?.providers ?: listOf()
    }

    override suspend fun save(provider: MgoOrganization) {
        // Get stored health care providers
        val storedMgoOrganizations = encryptedFileStore.getFile(MgoOrganizations::class, fileName) ?: MgoOrganizations(listOf())

        // Add our provider we want to save
        val newProviders = storedMgoOrganizations.providers.toMutableList()
        val alreadyAdded = newProviders.map { organization -> organization.id }.contains(provider.id)
        if (!alreadyAdded) {
            newProviders.add(provider)
        }
        val newStoredOrganizations = storedMgoOrganizations.copy(providers = newProviders)

        // Save new file
        encryptedFileStore.saveFile(value = newStoredOrganizations, clazz = MgoOrganizations::class, name = fileName)

        // Update flow
        storedOrganizationsFlow.value = newStoredOrganizations.providers
    }

    override suspend fun delete(providerId: String) {
        // Get stored health care providers
        val storedMgoOrganizations = requireNotNull(encryptedFileStore.getFile(MgoOrganizations::class, fileName))

        // Delete the provider from the file
        val newProviders = storedMgoOrganizations.providers.toMutableList()
        newProviders.removeIf { provider -> provider.id == providerId }
        val newStoredOrganizations = storedMgoOrganizations.copy(providers = newProviders)

        // Save new file
        encryptedFileStore.saveFile(value = newStoredOrganizations, clazz = MgoOrganizations::class, name = fileName)

        // Update flow
        storedOrganizationsFlow.value = newStoredOrganizations.providers
    }

    override suspend fun deleteAll() {
        // Update flow
        storedOrganizationsFlow.value = listOf()

        // Delete file
        encryptedFileStore.deleteFile(fileName)
    }
}
