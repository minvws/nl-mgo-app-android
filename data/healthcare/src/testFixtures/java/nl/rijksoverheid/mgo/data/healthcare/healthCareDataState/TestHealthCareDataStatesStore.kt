package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.HealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestHealthCareDataStatesStore : HealthCareDataStatesStore {
  private val stateFlow = MutableStateFlow(listOf<HealthCareDataState>())

  override fun get(): List<HealthCareDataState> = stateFlow.value

  override fun observe(
    category: HealthCareCategoryId,
    filterOrganization: MgoOrganization?,
  ): Flow<List<HealthCareDataState>> = stateFlow

  override suspend fun add(
    organization: MgoOrganization,
    category: HealthCareCategoryId,
    state: HealthCareDataState,
  ) {
    val currentList = stateFlow.value.toMutableList()
    stateFlow.value = currentList.toMutableList().also { list -> list.add(state) }
  }

  override suspend fun delete(organization: MgoOrganization) {
    stateFlow.value = listOf()
  }

  override suspend fun deleteAll() {
    stateFlow.value = listOf()
  }
}
