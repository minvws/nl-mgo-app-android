package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
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

    fun delete(organization: MgoOrganization)
}
