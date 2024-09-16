package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface HealthCareRepository {
    suspend fun getMedications(organization: MgoOrganization)

    fun observeData(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization? = null,
    ): Flow<List<HealthCareData>>
}
