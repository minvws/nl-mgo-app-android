package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestHealthCareDataStateRepository : HealthCareDataStateRepository {
  private var states: MutableMap<Pair<MgoOrganization, HealthCareCategoryId>, HealthCareDataState> = mutableMapOf()

  fun setLoadedState(
    organization: MgoOrganization,
    category: HealthCareCategoryId,
  ) {
    this.states[Pair(organization, category)] =
      TEST_HEALTH_CARE_DATA_STATE_LOADED.copy(
        organization = organization,
        category = category,
      )
  }

  override fun get(
    organization: MgoOrganization,
    category: HealthCareCategoryId,
  ): Flow<HealthCareDataState> {
    val state = this.states[Pair(organization, category)]
    return flow {
      state?.let {
        emit(it)
      }
    }
  }
}
