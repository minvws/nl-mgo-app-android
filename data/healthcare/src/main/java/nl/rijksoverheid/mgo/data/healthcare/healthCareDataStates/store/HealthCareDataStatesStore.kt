package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Store that handles [HealthCareDataState].
 */
internal interface HealthCareDataStatesStore {
  /**
   * @return A list of [HealthCareDataState] that are stored.
   */
  fun get(): List<HealthCareDataState>

  /**
   * Observes changes to the stored [HealthCareDataState] based on the given parameters.
   *
   * @param category The [HealthCareCategory] to filter the observed states.
   * @param filterOrganization If provided, only observes [HealthCareDataState] associated with this [MgoOrganization].
   * @return A [Flow] that emits the latest list of [HealthCareDataState] objects matching the given criteria.
   */
  fun observe(
    category: HealthCareCategory,
    filterOrganization: MgoOrganization?,
  ): Flow<List<HealthCareDataState>>

  /**
   * Add a [HealthCareDataState] to the store.
   *
   * @param organization The [MgoOrganization] in [HealthCareDataState] used for caching purposes.
   * @param category The [HealthCareCategory] in [HealthCareDataState] used for caching purposes.
   * @param state The [HealthCareDataState] to add to the store.
   */
  suspend fun add(
    organization: MgoOrganization,
    category: HealthCareCategory,
    state: HealthCareDataState,
  )

  /**
   * Deletes all [HealthCareDataState] in the store for a certain [MgoOrganization].
   *
   * @param organization The [MgoOrganization] to determine which [HealthCareDataState] objects need to be removed from the store.
   */
  suspend fun delete(organization: MgoOrganization)
}
