package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestHealthCareDataStatesRepository(
  initialData: List<HealthCareDataState>,
) : HealthCareDataStatesRepository {
  private val refreshData = mutableListOf<HealthCareDataState>()
  private val stateFlow = MutableStateFlow(initialData)

  fun setRefreshData(data: List<HealthCareDataState>) {
    this.refreshData.clear()
    this.refreshData.addAll(data)
  }

  override fun get(): List<HealthCareDataState> = stateFlow.value

  override suspend fun refresh(
    organization: MgoOrganization,
    category: HealthCareCategoryId,
  ) {
    stateFlow.value = refreshData
  }

  override fun observe(
    category: HealthCareCategoryId,
    filterOrganization: MgoOrganization?,
  ): Flow<List<HealthCareDataState>> = stateFlow

  override suspend fun delete(organization: MgoOrganization) {
    stateFlow.value = listOf()
    refreshData.clear()
  }

  override suspend fun deleteAll() {
    stateFlow.value = listOf()
    refreshData.clear()
  }
}
