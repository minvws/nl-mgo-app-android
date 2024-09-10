package nl.rijksoverheid.mgo.data.healthcare

import kotlinx.coroutines.flow.Flow

interface HealthCareRepository {
    suspend fun init()

    fun observeData(category: HealthCareCategory): Flow<List<HealthCareData>>
}
