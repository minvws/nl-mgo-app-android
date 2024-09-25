package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface HealthCareStateRepository {
    suspend fun init()

    suspend fun refresh(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization?,
    )

    fun observe(
        category: HealthCareCategory,
        organization: MgoOrganization? = null,
    ): Flow<List<HealthCareDataState>>
}
