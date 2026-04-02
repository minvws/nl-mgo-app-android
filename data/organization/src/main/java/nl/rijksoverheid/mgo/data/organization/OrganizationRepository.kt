package nl.rijksoverheid.mgo.data.organization

import androidx.annotation.VisibleForTesting
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.decodeToSequence
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.MgoOrganizationId
import nl.rijksoverheid.mgo.component.organization.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.organization.api.OrganizationApiClient
import timber.log.Timber
import java.io.InputStream
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class OrganizationRepository
  @Inject
  constructor(
    driver: SqlDriver,
    private val apiClient: OrganizationApiClient,
    @Named("supportedDataServiceIds") private val supportedDataServiceIds: List<String>,
  ) {
    private val json = Json { ignoreUnknownKeys = true }
    private val database = OrganizationsDatabase(driver)

    suspend fun sync(): Boolean =
      coroutineScope {
        val organizationsJob =
          async {
            apiClient.getOrganizations().mapCatching { (organizations, cached) ->
              if (cached) {
                Timber.v("Got organizations from cache")
                Result.success(true)
              } else {
                Timber.v("Got organizations from remote")
                insertOrganisationsInDb(organizations)
              }
            }
          }

        val endpointsJob =
          async {
            apiClient.getEndpoints().mapCatching { (endpoints, cached) ->
              if (cached) {
                Timber.v("Got endpoints from cache")
                Result.success(false)
              } else {
                Timber.v("Got endpoints from remote")
                insertEndpointsInDb(endpoints)
              }
            }
          }

        val organizationsResult = organizationsJob.await()
        val endpointsResult = endpointsJob.await()

        organizationsResult.isSuccess && endpointsResult.isSuccess
      }

    @OptIn(ExperimentalSerializationApi::class)
    private fun insertOrganisationsInDb(organizations: InputStream) {
      organizations.use {
        database.organizationQueries.transaction {
          database.organizationQueries.deleteAllOrganizations()
          json
            .decodeToSequence<Organization>(organizations)
            .chunked(100)
            .forEach { organizations ->
              for (organization in organizations) {
                database.organizationQueries.insertOrganization(
                  id = organization.id,
                  displayName = organization.displayName,
                  addressLine = organization.addressLine,
                  dataServicesJson = json.encodeToString(organization.dataServices),
                  searchBlob = organization.searchBlob.normalizeText(),
                )
              }
              Timber.v("Inserted: ${organizations.size} organizations in the database")
            }
        }
      }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun insertEndpointsInDb(endpoints: InputStream) {
      database.organizationQueries.transaction {
        database.organizationQueries.deleteAllEndpoints()
        val endpoints: Map<String, String> = json.decodeFromStream(endpoints)
        endpoints.asSequence().chunked(100).forEach { endpoint ->
          for (endpoint in endpoints) {
            database.organizationQueries.insertEndpoint(
              id = endpoint.key,
              url = endpoint.value,
            )
          }
          Timber.v("Inserted: ${endpoints.size} endpoints in the database")
        }
      }
    }

    fun search(
      query: String,
      context: CoroutineContext,
    ): Flow<List<MgoOrganization>> =
      database.organizationQueries
        .searchOrganizations(query.toFts5Query())
        .asFlow()
        .mapToList(context)
        .map { searchResults ->
          searchResults.map { searchResult ->
            Organization(
              id = searchResult.id ?: "",
              displayName = searchResult.displayName ?: "",
              searchBlob = searchResult.searchBlob ?: "",
              addressLine = searchResult.addressLine ?: "",
              added = searchResult.added != 0L,
              dataServices = searchResult.dataServicesJson?.let { json.decodeFromString(it) },
            )
          }
        }.map { organizations ->
          organizations.map { organization ->
            organization.toMgoOrganization(
              supportedDataServiceIds = supportedDataServiceIds,
              getEndpoint = { id ->
                database
                  .organizationQueries
                  .getEndpointById(id)
                  .executeAsOneOrNull()
                  ?.url
              },
            )
          }
        }

    fun getSaved(context: CoroutineContext): Flow<List<MgoOrganization>> =
      database.organizationQueries
        .getSavedOrganizations()
        .asFlow()
        .mapToList(context)
        .map { searchResults ->
          searchResults.map { searchResult ->
            Organization(
              id = searchResult.id ?: "",
              displayName = searchResult.displayName ?: "",
              searchBlob = searchResult.searchBlob ?: "",
              addressLine = searchResult.addressLine ?: "",
              added = searchResult.added != 0L,
              dataServices = searchResult.dataServicesJson?.let { json.decodeFromString(it) },
            )
          }
        }.map { organizations ->
          organizations.map { organization ->
            organization.toMgoOrganization(
              supportedDataServiceIds = supportedDataServiceIds,
              getEndpoint = { id ->
                database
                  .organizationQueries
                  .getEndpointById(id)
                  .executeAsOneOrNull()
                  ?.url
              },
            )
          }
        }

    fun save(organizationId: String) {
      database.organizationQueries.insertSavedOrganization(organizationId)
    }

    fun delete(organizationId: String) {
      database.organizationQueries.deleteSavedOrganization(organizationId)
    }

    fun deleteAllSaved() {
      database.organizationQueries.deleteAllSavedOrganizations()
    }

    /**
     * Only for testing purposes so we can quickly add and save a organization to the database.
     */
    @VisibleForTesting
    fun addAndSave(organization: MgoOrganization) {
      val dataService = listOf(Organization.DataService(id = TEST_BGZ_DATA_SERVICE.id, authEndpointId = "1", tokenEndpointId = "1", resourceEndpointId = "1"))
      database.organizationQueries.insertOrganization(
        id = organization.id,
        displayName = organization.name,
        addressLine = organization.address,
        dataServicesJson = json.encodeToString(dataService),
        searchBlob = null,
      )
      database.organizationQueries.insertEndpoint(id = "1", url = "")
      save(organization.id)
    }
  }
