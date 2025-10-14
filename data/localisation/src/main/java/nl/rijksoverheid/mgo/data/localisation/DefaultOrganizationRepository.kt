package nl.rijksoverheid.mgo.data.localisation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.load.LoadApi
import nl.rijksoverheid.mgo.data.api.load.SearchRequestBody
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizations
import nl.rijksoverheid.mgo.data.localisation.models.toMgoOrganization
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class DefaultOrganizationRepository
  @Inject
  constructor(
    private val loadApi: LoadApi,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
  ) : OrganizationRepository {
    private val json = Json.Default
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
      val organizationsJson = mgoByteArrayStorage.get(fileName)?.toString(Charsets.UTF_8)
      if (organizationsJson == null) {
        return listOf()
      } else {
        val organizations = json.decodeFromString<MgoOrganizations>(organizationsJson)
        return organizations.providers
      }
    }

    override suspend fun save(provider: MgoOrganization) {
      // Get stored health care providers
      val organizationsJson = mgoByteArrayStorage.get(fileName)?.toString(Charsets.UTF_8)
      val organizations = if (organizationsJson == null) MgoOrganizations(listOf()) else json.decodeFromString<MgoOrganizations>(organizationsJson)

      // Add our provider we want to save
      val newProviders = organizations.providers.toMutableList()
      val alreadyAdded = newProviders.map { organization -> organization.id }.contains(provider.id)
      if (!alreadyAdded) {
        newProviders.add(provider)
      }
      val newStoredOrganizations = organizations.copy(providers = newProviders)

      // Save new file
      val newOrganizationsJson = json.encodeToString(newStoredOrganizations).toByteArray()
      mgoByteArrayStorage.delete(fileName)
      mgoByteArrayStorage.save(name = fileName, content = newOrganizationsJson)

      // Update flow
      storedOrganizationsFlow.value = newStoredOrganizations.providers
    }

    override suspend fun delete(providerId: String) {
      // Get stored health care providers
      val organizationsJson = mgoByteArrayStorage.get(fileName)?.toString(Charsets.UTF_8)
      val organizations = if (organizationsJson == null) MgoOrganizations(listOf()) else json.decodeFromString<MgoOrganizations>(organizationsJson)

      // Delete the provider from the file
      val newProviders = organizations.providers.toMutableList()
      newProviders.removeIf { provider -> provider.id == providerId }
      val newStoredOrganizations = organizations.copy(providers = newProviders)

      // Save new file
      val newOrganizationsJson = json.encodeToString(newStoredOrganizations).toByteArray()
      mgoByteArrayStorage.delete(fileName)
      mgoByteArrayStorage.save(name = fileName, content = newOrganizationsJson)

      // Update flow
      storedOrganizationsFlow.value = newStoredOrganizations.providers
    }

    override suspend fun deleteAll() {
      // Update flow
      storedOrganizationsFlow.value = listOf()

      // Delete file
      mgoByteArrayStorage.delete(fileName)
    }
  }
