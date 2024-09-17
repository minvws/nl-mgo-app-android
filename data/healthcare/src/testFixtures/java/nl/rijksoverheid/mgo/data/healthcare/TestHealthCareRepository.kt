package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHealthCareRepository : HealthCareRepository {
    private val data: MutableMap<HealthCareCategory, List<HealthCareData>> = mutableMapOf()

    fun addHealthCareData(
        category: HealthCareCategory,
        data: List<HealthCareData>,
    ) {
        this.data[category] = data
    }

    override suspend fun getMedications(organization: MgoOrganization) {
        this.data[HealthCareCategory.MEDICATIONS] = listOf(TEST_HEALTH_CARE_DATA_LOADED_MEDICATION)
    }

    override fun observeData(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization?,
    ): Flow<List<HealthCareData>> {
        return flow { emit(data[category]!!) }
    }
}
