package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestHealthCareStateRepository(initialData: List<HealthCareDataState>) : HealthCareStateRepository {
    private val state = MutableStateFlow(initialData)

    private val refreshDataList = mutableListOf<HealthCareDataState>()

    fun setRefreshData(data: List<HealthCareDataState>) {
        this.refreshDataList.clear()
        this.refreshDataList.addAll(data)
    }

    override suspend fun init() {
    }

    override suspend fun refresh(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization?,
    ) {
        state.update { refreshDataList }
    }

    override fun observe(
        category: HealthCareCategory,
        organization: MgoOrganization?,
    ): Flow<List<HealthCareDataState>> {
        return state
    }
}
