package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store

import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface HealthCareDataStatesStore {
    fun get(): List<HealthCareDataState>

    fun observe(
        category: HealthCareCategory,
        filterOrganization: MgoOrganization?,
    ): Flow<List<HealthCareDataState>>

    suspend fun add(
        organization: MgoOrganization,
        category: HealthCareCategory,
        state: HealthCareDataState,
    )

    suspend fun delete(organization: MgoOrganization)
}
