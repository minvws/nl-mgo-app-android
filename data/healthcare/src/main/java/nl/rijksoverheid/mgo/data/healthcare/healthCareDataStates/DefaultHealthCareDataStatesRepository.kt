package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.HealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles various operations on lists of [HealthCareDataState].
 *
 * @param healthCareDataStateRepository The [HealthCareDataStateRepository] to handle operations on [HealthCareDataState].
 * @param healthCareDataStatesStore The [HealthCareDataStatesStore] to handle storage of [HealthCareDataState].
 */
@Singleton
internal class DefaultHealthCareDataStatesRepository
  @Inject
  constructor(
    private val healthCareDataStateRepository: HealthCareDataStateRepository,
    private val healthCareDataStatesStore: HealthCareDataStatesStore,
  ) : HealthCareDataStatesRepository {
    /**
     * @return A list of [HealthCareDataState] that are stored.
     */
    override fun get(): List<HealthCareDataState> = healthCareDataStatesStore.get()

    /**
     * Fetches health care data and adds it to the store.
     * @param organization The [MgoOrganization] to fetch health care data from.
     * @param category The [HealthCareCategoryId] to fetch health care data from.
     */
    override suspend fun refresh(
      organization: MgoOrganization,
      category: HealthCareCategoryId,
    ) {
      healthCareDataStateRepository.get(organization = organization, category = category).collectLatest { state ->
        healthCareDataStatesStore.add(organization = organization, category = category, state = state)
      }
    }

    /**
     * Observes changes to the stored [HealthCareDataState] based on the given parameters.
     *
     * @param category The [HealthCareCategoryId] to filter the observed states.
     * @param filterOrganization If provided, only observes [HealthCareDataState] associated with this [MgoOrganization].
     * @return A [Flow] that emits the latest list of [HealthCareDataState] objects matching the given criteria.
     */
    override fun observe(
      category: HealthCareCategoryId,
      filterOrganization: MgoOrganization?,
    ): Flow<List<HealthCareDataState>> = healthCareDataStatesStore.observe(category = category, filterOrganization = filterOrganization)

    /**
     * Deletes all [HealthCareDataState] in the store for a certain [MgoOrganization].
     *
     * @param organization The [MgoOrganization] to determine which [HealthCareDataState] objects need to be removed from the store.
     */
    override suspend fun delete(organization: MgoOrganization) = healthCareDataStatesStore.delete(organization)

    override suspend fun deleteAll() {
      healthCareDataStatesStore.deleteAll()
    }
  }
