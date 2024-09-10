package nl.rijksoverheid.mgo.data.healthcare

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHealthCareRepository : HealthCareRepository {
    private val data: MutableMap<HealthCareCategory, List<HealthCareData>> = mutableMapOf()

    fun setData(
        category: HealthCareCategory,
        data: List<HealthCareData>,
    ) {
        this.data.put(category, data)
    }

    override suspend fun init() {
    }

    override fun observeData(category: HealthCareCategory): Flow<List<HealthCareData>> {
        return flow { emit(data[category]!!) }
    }
}
