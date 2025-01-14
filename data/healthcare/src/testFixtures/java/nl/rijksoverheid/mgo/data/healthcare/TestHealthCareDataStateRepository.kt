package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHealthCareDataStateRepository : HealthCareDataStateRepository {
    private var states: MutableMap<Pair<MgoOrganization, HealthCareCategory>, HealthCareDataState> = mutableMapOf()

    private var state: HealthCareDataState? = null

    fun setLoadedState(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ) {
        this.states[Pair(organization, category)] =
            TEST_HEALTH_CARE_DATA_STATE_LOADED.copy(
                organization = organization,
                category = category,
            )
    }

    override fun get(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): Flow<HealthCareDataState> {
        val state = this.states[Pair(organization, category)]
        return flow {
            state?.let {
                emit(it)
            }
        }
    }
}
