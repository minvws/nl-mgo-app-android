package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestHealthCareDataStatesRepository(initialData: List<HealthCareDataState>) :
    HealthCareDataStatesRepository {
    private val refreshData = mutableListOf<HealthCareDataState>()
    private val stateFlow = MutableStateFlow(initialData)

    fun setRefreshData(data: List<HealthCareDataState>) {
        this.refreshData.clear()
        this.refreshData.addAll(data)
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

    override fun delete(organization: MgoOrganization) {
        stateFlow.value = listOf()
        refreshData.clear()
    }
}
