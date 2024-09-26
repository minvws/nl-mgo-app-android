package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface HealthCareDataStatesRepository {
    suspend fun refresh(
        organization: MgoOrganization,
        category: HealthCareCategory,
    )

    fun observe(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization?,
    ): Flow<List<HealthCareDataState>>
}
