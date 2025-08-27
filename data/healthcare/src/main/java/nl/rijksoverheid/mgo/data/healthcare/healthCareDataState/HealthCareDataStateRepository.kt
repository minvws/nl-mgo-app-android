package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Handles various operations on [HealthCareDataState].
 */
internal interface HealthCareDataStateRepository {
  /**
   * Emits multiple [HealthCareDataState] that represent the current state of fetching health care data.
   *
   * @param organization The [MgoOrganization] to fetch health care data from.
   * @param category The [HealthCareCategoryId] to fetch health care data from.
   * @return [Flow] emits the current state of the health care data.
   */
  fun get(
    organization: MgoOrganization,
    category: HealthCareCategoryId,
  ): Flow<HealthCareDataState>
}
