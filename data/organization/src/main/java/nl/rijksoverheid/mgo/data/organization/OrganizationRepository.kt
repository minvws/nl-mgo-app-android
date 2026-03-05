package nl.rijksoverheid.mgo.data.organization

import android.content.Context
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import nl.rijksoverheid.mgo.component.organization.Organization
import nl.rijksoverheid.mgo.framework.util.file.ReadLocalFile
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class OrganizationRepository
  @Inject
  constructor(
    driver: SqlDriver,
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
                  searchBlob = organization.searchBlob.normalizeText(),
                )
              }
            }
          }
      }
    }

    suspend fun search(query: String): Flow<List<Organization>> =
      database.organizationQueries
        .searchOrganizations(query.toFts5Query(fuzzy = true))
        .asFlow()
        .mapToList(coroutineContext)
        .map { searchResults ->
          searchResults.map { searchResult ->
            Organization(
              id = searchResult.id ?: "",
              displayName = searchResult.displayName ?: "",
              searchBlob = searchResult.searchBlob ?: "",
            )
          }
        }
  }
