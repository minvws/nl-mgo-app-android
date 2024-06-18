package nl.rijksoverheid.mgo.data.localisation

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.load.LoadApi
import nl.rijksoverheid.mgo.data.api.load.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProviders
import nl.rijksoverheid.mgo.data.localisation.models.toHealthCareProvider
import nl.rijksoverheid.mgo.framework.storage.file.FileStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

internal class DefaultHealthCareProviderRepository(
    private val loadApi: LoadApi,
    private val
    fileStore: FileStore,
) :
    HealthCareProviderRepository {
    private val fileName = "healthcareproviders.json"

    override val storedHealthCareProvidersFlow: MutableStateFlow<List<HealthCareProvider>> = MutableStateFlow(runBlocking { get() })

    override suspend fun search(
        name: String,
        city: String,
    ): Flow<List<HealthCareProvider>> {
        val requestBody =
            SearchRequestBody(name = name, city = city)
        val searchResponseFlow =
            flow {
                val result = executeNetworkRequest { loadApi.search(requestBody) }
                emit(result.getOrThrow())
            }
        return combine(searchResponseFlow, storedHealthCareProvidersFlow) { searchResponse, storedHealthCareProviders ->
            searchResponse.organizations.map { organization ->
                organization.toHealthCareProvider(added = storedHealthCareProviders.any { provider -> provider.id == organization.id })
            }
        }
    }

    override suspend fun get(): List<HealthCareProvider> {
        val localHealthCareProviders = fileStore.getFile(HealthCareProviders::class.java, fileName)
        return localHealthCareProviders?.providers ?: listOf()
    }

    override suspend fun save(provider: HealthCareProvider) {
        // Get stored health care providers
        val storedHealthCareProviders = fileStore.getFile(HealthCareProviders::class.java, fileName) ?: HealthCareProviders(listOf())

        // Add our provider we want to save
        val newProviders = storedHealthCareProviders.providers.toMutableList()
        newProviders.add(provider)
        val newStoredHealthCareProviders = storedHealthCareProviders.copy(providers = newProviders)

        // Save new file
        fileStore.saveFile(file = newStoredHealthCareProviders, name = fileName)

        // Update flow
        storedHealthCareProvidersFlow.value = newStoredHealthCareProviders.providers
    }

    override suspend fun delete(providerId: String) {
        // Get stored health care providers
        val storedHealthCareProviders = requireNotNull(fileStore.getFile(HealthCareProviders::class.java, fileName))

        // Delete the provider from the file
        val newProviders = storedHealthCareProviders.providers.toMutableList()
        newProviders.removeIf { provider -> provider.id == providerId }
        val newStoredHealthCareProviders = storedHealthCareProviders.copy(providers = newProviders)

        // Save new file
        fileStore.saveFile(file = newStoredHealthCareProviders, name = fileName)

        // Update flow
        storedHealthCareProvidersFlow.value = newStoredHealthCareProviders.providers
    }
}
