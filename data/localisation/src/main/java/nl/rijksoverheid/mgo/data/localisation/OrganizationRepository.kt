package nl.rijksoverheid.mgo.data.localisation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import nl.nl.rijksoverheid.mgo.framework.network.executeRequest
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.MgoOrganizations
import nl.rijksoverheid.mgo.data.localisation.api.DataServiceId
import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse
import nl.rijksoverheid.mgo.data.localisation.api.toMgoOrganization
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class OrganizationRepository
  @Inject
  constructor(
    private val okHttpClient: OkHttpClient,
    @Named("loadApiBaseUrl") private val baseUrl: String,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
  ) {
    private val json = Json { ignoreUnknownKeys = true }
    private val fileName = "organizations.json"

    val storedOrganizationsFlow: MutableStateFlow<List<MgoOrganization>> =
      MutableStateFlow(runBlocking { get() })

    fun search(
      name: String,
      city: String,
      supportedDataServiceIds: List<DataServiceId>,
    ): Flow<List<MgoOrganization>> {
      val searchResponseFlow =
        flow {
          val requestBodyJson =
            buildJsonObject {
              put("name", name.trim())
              put("city", city.trim())
            }
          val requestBodyString = json.encodeToString(requestBodyJson)
          val request =
            Request
              .Builder()
              .url("$baseUrl/localization/organization/search")
              .post(requestBodyString.toRequestBody("application/json".toMediaType()))
              .build()
          okHttpClient
            .executeRequest(request)
            .onSuccess { response ->
              val responseJson = response.body?.string() ?: throw RuntimeException("Empty response body")
              val searchResponse = json.decodeFromString<SearchResponse>(responseJson)
              emit(searchResponse)
            }.onFailure { error ->
              throw error
            }
        }
      return combine(searchResponseFlow, storedOrganizationsFlow) { searchResponse, storedOrganizations ->
        searchResponse.organizations.map { organization ->
          organization.toMgoOrganization(
            added =
              storedOrganizations.any { provider ->
                provider.id == organization.id
              },
            supportedDataServiceIds = supportedDataServiceIds,
          )
        }
      }
    }

    fun searchDemo(supportedDataServiceIds: List<DataServiceId>): Flow<List<MgoOrganization>> {
      val searchResponseFlow =
        flow {
          val request =
            Request
              .Builder()
              .url("$baseUrl/localization/organization/search-demo")
              .post("".toRequestBody("application/json".toMediaType()))
              .build()
          okHttpClient
            .executeRequest(request)
            .onSuccess { response ->
              val responseJson = response.body?.string() ?: throw RuntimeException("Empty response body")
              val searchResponse = json.decodeFromString<SearchResponse>(responseJson)
              emit(searchResponse)
            }.onFailure { error ->
              throw error
            }
        }
      return combine(searchResponseFlow, storedOrganizationsFlow) { searchResponse, storedOrganizations ->
        searchResponse.organizations.map { organization ->
          organization.toMgoOrganization(
            added =
              storedOrganizations.any { provider ->
                provider.id == organization.id
              },
            supportedDataServiceIds = supportedDataServiceIds,
          )
        }
      }
    }

    suspend fun get(): List<MgoOrganization> {
      val organizationsJson = mgoByteArrayStorage.get(fileName)?.toString(Charsets.UTF_8)
      if (organizationsJson == null) {
        return listOf()
      } else {
        val organizations = json.decodeFromString<MgoOrganizations>(organizationsJson)
        return organizations.providers
      }
    }

    suspend fun save(provider: MgoOrganization) {
      // Get stored health care providers
      val organizationsJson = mgoByteArrayStorage.get(fileName)?.toString(Charsets.UTF_8)
      val organizations =
        if (organizationsJson ==
          null
        ) {
          nl.rijksoverheid.mgo.component.organization
            .MgoOrganizations(listOf())
        } else {
          json.decodeFromString<MgoOrganizations>(organizationsJson)
        }

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

    suspend fun delete(providerId: String) {
      // Get stored health care providers
      val organizationsJson = mgoByteArrayStorage.get(fileName)?.toString(Charsets.UTF_8)
      val organizations =
        if (organizationsJson ==
          null
        ) {
          nl.rijksoverheid.mgo.component.organization
            .MgoOrganizations(listOf())
        } else {
          json.decodeFromString<MgoOrganizations>(organizationsJson)
        }

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

    suspend fun deleteAll() {
      // Update flow
      storedOrganizationsFlow.value = listOf()

      // Delete file
      mgoByteArrayStorage.delete(fileName)
    }
  }
