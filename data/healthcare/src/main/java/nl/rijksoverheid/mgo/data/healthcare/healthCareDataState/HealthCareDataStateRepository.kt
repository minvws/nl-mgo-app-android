package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface HealthCareDataStateRepository {
    fun get(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): Flow<HealthCareDataState>
}
