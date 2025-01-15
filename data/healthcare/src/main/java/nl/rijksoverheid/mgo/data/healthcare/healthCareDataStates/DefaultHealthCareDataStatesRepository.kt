package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.HealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

/**
 * Holds state of fetched health care data. The health care data is linked to both [MgoOrganization] and [HealthCareCategory].
 * This way it is possible to get all health care related data based on a category, or a category and organization.
 */
@Singleton
internal class DefaultHealthCareDataStatesRepository
    @Inject
    constructor(
        private val healthCareDataStateRepository: HealthCareDataStateRepository,
        private val healthCareDataStatesStore: HealthCareDataStatesStore,
    ) : HealthCareDataStatesRepository {
        private data class StateKey(val organization: MgoOrganization, val category: HealthCareCategory)

        private val statesFlow = MutableStateFlow<Map<StateKey, HealthCareDataState>>(mapOf())

        override fun get(): List<HealthCareDataState> {
            return statesFlow.value.map { it.value }
        }

        /**
         * Refreshes health care data.
         * @param organization The organization you want to fetch health care data from.
         * @param category The category of health care data it should fetch.
         */
        override suspend fun refresh(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ) {
            healthCareDataStateRepository.get(organization = organization, category = category).collectLatest { state ->
                healthCareDataStatesStore.add(organization = organization, category = category, state = state)
            }
        }

        /**
         * Observes health care data states.
         * @param category The category to listen to.
         * @param filterOrganization The organization to listen to. When null, will fetch all health care data states for all organizations.
         */
        override fun observe(
            category: HealthCareCategory,
            filterOrganization: MgoOrganization?,
        ): Flow<List<HealthCareDataState>> {
            return healthCareDataStatesStore.observe(category = category, filterOrganization = filterOrganization)
        }

        /**
         * Delete health care data states. Will delete all data (all categories) for a particular organization.
         * @param organization The organization to delete.
         */
        override suspend fun delete(organization: MgoOrganization) {
            return healthCareDataStatesStore.delete(organization)
        }
    }
