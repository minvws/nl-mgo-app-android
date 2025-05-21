package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

class TestHealthCareDataStatesRepository(initialData: List<HealthCareDataState>) :
  HealthCareDataStatesRepository {
  private val refreshData = mutableListOf<HealthCareDataState>()
  private val stateFlow = MutableStateFlow(initialData)

  fun setRefreshData(data: List<HealthCareDataState>) {
    this.refreshData.clear()
    this.refreshData.addAll(data)
  }

  override fun get(): List<HealthCareDataState> {
    return stateFlow.value
  }

  override suspend fun refresh(
    organization: MgoOrganization,
    category: HealthCareCategory,
  ) {
    stateFlow.value = refreshData
  }

  override fun observe(
    category: HealthCareCategory,
    filterOrganization: MgoOrganization?,
  ): Flow<List<HealthCareDataState>> {
    return stateFlow
  }

  override suspend fun delete(organization: MgoOrganization) {
    stateFlow.value = listOf()
    refreshData.clear()
  }
}
