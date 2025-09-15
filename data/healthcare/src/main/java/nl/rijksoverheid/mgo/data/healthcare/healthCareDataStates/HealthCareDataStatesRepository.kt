package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Handles various operations on lists of [HealthCareDataState].
 */
interface HealthCareDataStatesRepository {
  /**
   * @return A list of [HealthCareDataState] that are stored.
   */
  fun get(): List<HealthCareDataState>

  /**
   * Fetches health care data and adds it to the store.
   * @param organization The [MgoOrganization] to fetch health care data from.
   * @param category The [HealthCareCategoryId] to fetch health care data from.
   */
  suspend fun refresh(
    organization: MgoOrganization,
    category: HealthCareCategoryId,
  )

  /**
   * Observes changes to the stored [HealthCareDataState] based on the given parameters.
   *
   * @param category The [HealthCareCategoryId] to filter the observed states.
   * @param filterOrganization If provided, only observes [HealthCareDataState] associated with this [MgoOrganization].
   * @return A [Flow] that emits the latest list of [HealthCareDataState] objects matching the given criteria.
   */
  fun observe(
    category: HealthCareCategoryId,
    filterOrganization: MgoOrganization?,
  ): Flow<List<HealthCareDataState>>

  /**
   * Deletes all [HealthCareDataState] in the store for a certain [MgoOrganization].
   *
   * @param organization The [MgoOrganization] to determine which [HealthCareDataState] objects need to be removed from the store.
   */
  suspend fun delete(organization: MgoOrganization)

  suspend fun deleteAll()
}
