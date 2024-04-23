package nl.rijksoverheid.mgo.data.localisation

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.localisation.api.SearchApi
import nl.rijksoverheid.mgo.data.localisation.api.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProviders
import nl.rijksoverheid.mgo.data.localisation.models.toHealthCareProviders
import nl.rijksoverheid.mgo.framework.storage.file.FileStore

internal class DefaultHealthCareProviderRepository(private val searchApi: SearchApi, private val fileStore: FileStore) :
    HealthCareProviderRepository {
    private val fileName = "healthcareproviders.json"

    override suspend fun search(
        name: String,
        city: String,
    ): Result<List<HealthCareProvider>> {
        val requestBody = SearchRequestBody(name = name, city = city)
        val result = executeNetworkRequest { searchApi.search(requestBody) }
        return result
            .mapCatching { response -> response.toHealthCareProviders() }
    }

    override suspend fun get(): List<HealthCareProvider> {
        val localHealthCareProviders = fileStore.getFile(HealthCareProviders::class.java, fileName)
        return localHealthCareProviders?.providers ?: listOf()
    }

    override suspend fun save(provider: HealthCareProvider) {
        // Get locally stored health care providers
        val localHealthCareProviders = fileStore.getFile(HealthCareProviders::class.java, fileName) ?: HealthCareProviders(listOf())

        // Add our provider we want to save
        val newProviders = localHealthCareProviders.providers.toMutableList()
        newProviders.add(provider)
        val newLocalHealthCareProviders = localHealthCareProviders.copy(providers = newProviders)

        // Save new file
        fileStore.saveFile(file = newLocalHealthCareProviders, name = fileName)
    }

    override suspend fun delete(provider: HealthCareProvider) {
        // Get locally stored health care providers
        val localHealthCareProviders = requireNotNull(fileStore.getFile(HealthCareProviders::class.java, fileName))

        // Delete the provider from the file
        val newProviders = localHealthCareProviders.providers.toMutableList()
        newProviders.remove(provider)
        val newLocalHealthCareProviders = localHealthCareProviders.copy(providers = newProviders)

        // Save new file
        fileStore.saveFile(file = newLocalHealthCareProviders, name = fileName)
    }
}
