package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update

@Singleton
internal class DefaultHealthCareDataStatesRepository
    @Inject
    constructor(
        private val healthCareDataStateRepository: HealthCareDataStateRepository,
    ) : HealthCareDataStatesRepository {
        private data class StateKey(val organization: MgoOrganization, val category: HealthCareCategory)

        private val statesFlow = MutableStateFlow<Map<StateKey, HealthCareDataState>>(mapOf())

        override suspend fun refresh(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ) {
            val stateKey = StateKey(organization = organization, category = category)
            healthCareDataStateRepository.get(organization = organization, category = category).collectLatest { state ->
                statesFlow.update { states -> states.toMutableMap().apply { put(stateKey, state) } }
            }
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
                val stateKey = StateKey(organization = filterOrganization, category = category)
                return statesFlow.mapNotNull { states ->
                    val state = states[stateKey] ?: return@mapNotNull null
                    listOf(state)
                }
            }
        }
    }
