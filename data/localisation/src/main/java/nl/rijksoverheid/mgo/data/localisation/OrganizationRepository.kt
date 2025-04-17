package nl.rijksoverheid.mgo.data.localisation

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Handles various operations on [MgoOrganization].
 */
interface OrganizationRepository {
  val storedOrganizationsFlow: Flow<List<MgoOrganization>>

  /**
   * Search for health care providers.
   *
   * @param name The name of the health care provider to search for.
   * @param city The city of the health care provider to search for.
   * @return [Flow] containing a list of [MgoOrganization] representing a health care provider.
   */
  fun search(
    name: String,
    city: String,
  ): Flow<List<MgoOrganization>>

  /**
   * Temporary: search for health care providers based on if they have data for you.
   * This talks to a api which returns hard coded data. It is for demo purposes only.
   *
   * @return [Flow] containing a list of [MgoOrganization] representing a health care provider.
   */
  suspend fun searchDemo(): Flow<List<MgoOrganization>>

  /**
   * @return All the [MgoOrganization] that are stored.
   */
  suspend fun get(): List<MgoOrganization>

  /**
   * Save a [MgoOrganization].
   *
   * @param provider The [MgoOrganization] to save.
   */
  suspend fun save(provider: MgoOrganization)

  /**
   * Delete a [MgoOrganization].
   *
   * @param providerId The id of the [MgoOrganization] to delete.
   */
  suspend fun delete(providerId: String)

  /**
   * Deletes all [MgoOrganization] that are stored.
   */
  suspend fun deleteAll()
}
