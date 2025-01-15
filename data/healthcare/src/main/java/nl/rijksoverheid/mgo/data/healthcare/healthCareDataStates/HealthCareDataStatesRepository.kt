package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface HealthCareDataStatesRepository {
    fun get(): List<HealthCareDataState>

    suspend fun refresh(
        organization: MgoOrganization,
        category: HealthCareCategory,
    )

    fun observe(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization?,
    ): Flow<List<HealthCareDataState>>

    suspend fun delete(organization: MgoOrganization)
}
