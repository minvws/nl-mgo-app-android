package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update

/**
 * Holds state of fetched health care data. The health care data is linked to both [MgoOrganization] and [HealthCareCategory].
 * This way it is possible to get all health care related data based on a category, or a category and organization.
 */
@Singleton
internal class DefaultHealthCareDataStatesRepository
    @Inject
    constructor(
        private val healthCareDataStateRepository: HealthCareDataStateRepository,
    ) : HealthCareDataStatesRepository {
        private data class StateKey(val organization: MgoOrganization, val category: HealthCareCategory)

        private val statesFlow = MutableStateFlow<Map<StateKey, HealthCareDataState>>(mapOf())

        /**
         * Refreshes health care data.
         * @param organization The organization you want to fetch health care data from.
         * @param category The category of health care data it should fetch.
         */
        override suspend fun refresh(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ) {
            val stateKey = StateKey(organization = organization, category = category)
            healthCareDataStateRepository.get(organization = organization, category = category).collectLatest { state ->
                statesFlow.update { states -> states.toMutableMap().apply { put(stateKey, state) } }
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
            if (filterOrganization == null) {
                return statesFlow.mapNotNull { states ->
                    states.keys
                        .filter { key -> key.category == category }
                        .mapNotNull { key -> states[key] }
                }
            } else {
                val stateKey = StateKey(organization = filterOrganization, category = category)
                return statesFlow.mapNotNull { states ->
                    val state = states[stateKey] ?: return@mapNotNull null
                    listOf(state)
                }
            }
        }

        /**
         * Delete health care data states. Will delete all data (all categories) for a particular organization.
         * @param organization The organization to delete.
         */
        override fun delete(organization: MgoOrganization) {
            val stateKeys = statesFlow.value.keys.filter { key -> key.organization == organization }
            statesFlow.update { states ->
                states.toMutableMap().apply {
                    for (stateKey in stateKeys) {
                        remove(stateKey)
                    }
                }
            }
        }
    }
