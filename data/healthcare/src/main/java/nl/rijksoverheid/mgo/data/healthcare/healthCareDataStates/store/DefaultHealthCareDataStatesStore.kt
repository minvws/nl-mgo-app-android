package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject

/**
 * Store that handles [HealthCareDataState].
 */
internal class DefaultHealthCareDataStatesStore
  @Inject
  constructor() : HealthCareDataStatesStore {
    internal data class StateKey(
      val organization: MgoOrganization,
      val category: HealthCareCategoryId,
    )

    private val statesFlow = MutableStateFlow<Map<StateKey, HealthCareDataState>>(mapOf())

    /**
     * @return A list of [HealthCareDataState] that are stored in [statesFlow].
     */
    override fun get(): List<HealthCareDataState> = statesFlow.value.map { it.value }

    /**
     * Observes changes to the stored [HealthCareDataState] based on the given parameters.
     *
     * @param category The [HealthCareCategoryId] to filter the observed states.
     * @param filterOrganization If provided, only observes [HealthCareDataState] associated with this [MgoOrganization].
     * @return A [Flow] that emits the latest list of [HealthCareDataState] objects matching the given criteria.
     *         - If [filterOrganization] is null, it returns all states matching the specified [category].
     *         - If [filterOrganization] is provided, it returns only the state associated with the given organization and category.
     */
    override fun observe(
      category: HealthCareCategoryId,
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

    /**
     * Add a [HealthCareDataState] to the store.
     *
     * @param organization The [MgoOrganization] in [HealthCareDataState] used for caching purposes.
     * @param category The [HealthCareCategoryId] in [HealthCareDataState] used for caching purposes.
     * @param state The [HealthCareDataState] to add to the store.
     */
    override suspend fun add(
      organization: MgoOrganization,
      category: HealthCareCategoryId,
      state: HealthCareDataState,
    ) {
      val stateKey = StateKey(organization = organization, category = category)
      statesFlow.update { states -> states.toMutableMap().apply { put(stateKey, state) } }
    }

    /**
     * Deletes all [HealthCareDataState] in the store for a certain [MgoOrganization].
     *
     * @param organization The [MgoOrganization] to determine which [HealthCareDataState] objects need to be removed from the store.
     */
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

    override suspend fun deleteAll() {
      statesFlow.update { mapOf() }
    }
  }
