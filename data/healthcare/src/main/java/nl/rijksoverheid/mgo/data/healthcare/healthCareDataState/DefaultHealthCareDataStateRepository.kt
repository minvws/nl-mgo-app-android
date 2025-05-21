package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.getRequests
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject

/**
 * Handles various operations on [HealthCareDataState].
 *
 * @param mgoResourceRepository The [MgoResourceRepository] for getting [MgoResource]s.
 */
internal class DefaultHealthCareDataStateRepository
  @Inject
  constructor(private val mgoResourceRepository: MgoResourceRepository) :
  HealthCareDataStateRepository {
    /**
     * Emits multiple [HealthCareDataState] that represent the current state of fetching health care data.
     *
     * @param organization The [MgoOrganization] to fetch health care data from.
     * @param category The [HealthCareCategory] to fetch health care data from.
     * @return [Flow] emits the current state of the health care data.
     */
    override fun get(
      organization: MgoOrganization,
      category: HealthCareCategory,
    ): Flow<HealthCareDataState> =
      flow {
        // Emit loading state
        emit(HealthCareDataState.Loading(organization = organization, category = category))

        // Start fetching mgo resources
        val requests = category.getRequests()
        val results =
          requests.mapNotNull { request ->
            // Only do requests if the organization has the correct data service.
            // For example if Provider X only has data service for BGZ, and we want to make a request to GP.
            // Do not execute that request.
            val endpoint =
              organization.dataServices.firstOrNull { dataService ->
                dataService.type == request.dataServiceType
              }?.resourceEndpoint ?: return@mapNotNull null

            mgoResourceRepository.get(endpoint = endpoint, request = request)
          }

        val isEmpty = results.mapNotNull { result -> result.getOrNull() }.flatten().isEmpty()
        if (isEmpty) {
          // If there are no results, emit empty state
          emit(
            HealthCareDataState.Empty(
              organization = organization,
              category = category,
            ),
          )
        } else {
          // If there are results, emit loaded state
          emit(
            HealthCareDataState.Loaded(
              results = results,
              organization = organization,
              category = category,
            ),
          )
        }
      }
  }
