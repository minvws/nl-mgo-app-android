package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHealthCareStateRepository : HealthCareStateRepository {
    private val dataList = mutableListOf<HealthCareDataState>()

    fun setData(data: HealthCareDataState) {
        this.dataList.clear()
        this.dataList.add(data)
    }

    override suspend fun init() {
    }

    override suspend fun observe(
        category: HealthCareCategory,
        organization: MgoOrganization?,
    ): Flow<List<HealthCareDataState>> {
        return flow { emit(dataList) }
    }
}
