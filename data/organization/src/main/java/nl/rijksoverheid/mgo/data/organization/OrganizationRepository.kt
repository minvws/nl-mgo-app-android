package nl.rijksoverheid.mgo.data.organization

import android.content.Context
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class OrganizationRepository
  @Inject
  constructor(
    driver: SqlDriver,
    @Named("supportedDataServiceIds") private val supportedDataServiceIds: List<String>,
    @ApplicationContext private val context: Context,
  ) {
    private val json = Json { ignoreUnknownKeys = true }
    private val database = OrganizationsDatabase(driver)

    /**
     * Get all organizations and add them to the database.
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun sync(organizationsFile: String) {
      // Remove all organizations from database
      database.organizationQueries.deleteAllOrganizations()

      // Insert organizations into database
      val organizationsJson = context.assets.open(organizationsFile)
      organizationsJson.use { inputStream ->
        json
          .decodeToSequence<Organization>(inputStream)
          .chunked(100)
          .forEach { organizations ->
            database.organizationQueries.transaction {
              for (organization in organizations) {
                database.organizationQueries.insertOrganization(
                  id = organization.id,
                  displayName = organization.displayName,
                  addressLine = organization.addressLine,
                  city = organization.city,
                  dataServicesJson = json.encodeToString(organization.dataServices),
                  searchBlob = organization.searchBlob.normalizeText(),
                )
              }
            }
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
              city = searchResult.city ?: "",
              added = searchResult.added != 0L,
              dataServices = searchResult.dataServicesJson?.let { json.decodeFromString(it) } ?: mapOf(),
            )
          }
        }.map { organizations -> organizations.map { organization -> organization.toMgoOrganization(supportedDataServiceIds = supportedDataServiceIds) } }

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
              city = searchResult.city ?: "",
              added = searchResult.added != 0L,
              dataServices = searchResult.dataServicesJson?.let { json.decodeFromString(it) } ?: mapOf(),
            )
          }
        }.map { organizations -> organizations.map { organization -> organization.toMgoOrganization(supportedDataServiceIds = supportedDataServiceIds) } }

    fun save(organizationId: String) {
      database.organizationQueries.insertSavedOrganization(organizationId)
    }

    fun delete(organizationId: String) {
      database.organizationQueries.deleteSavedOrganization(organizationId)
    }
  }
