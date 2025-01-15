package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store

import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update

internal class DefaultHealthCareDataStatesStore
    @Inject
    constructor() : HealthCareDataStatesStore {
        internal data class StateKey(val organization: MgoOrganization, val category: HealthCareCategory)

        private val statesFlow = MutableStateFlow<Map<StateKey, HealthCareDataState>>(mapOf())

        override fun get(): List<HealthCareDataState> {
            return statesFlow.value.map { it.value }
        }

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
                val stateKey =
                    StateKey(
                        organization = filterOrganization,
                        category = category,
                    )
                return statesFlow.mapNotNull { states ->
                    val state = states[stateKey] ?: return@mapNotNull null
                    listOf(state)
                }
            }
        }

        override suspend fun add(
            organization: MgoOrganization,
            category: HealthCareCategory,
            state: HealthCareDataState,
        ) {
            val stateKey = StateKey(organization = organization, category = category)
            statesFlow.update { states -> states.toMutableMap().apply { put(stateKey, state) } }
        }

        override suspend fun delete(organization: MgoOrganization) {
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
