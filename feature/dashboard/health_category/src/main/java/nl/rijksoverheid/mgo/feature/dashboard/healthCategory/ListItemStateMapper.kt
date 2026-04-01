package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseErrorType
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.healthCategories.GetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.coroutineContext

internal class ListItemStateMapper
  @Inject
  constructor(
    private val listItemGroupMapper: ListItemGroupMapper,
    private val mgoResourceStore: MgoResourceStore,
    private val mgoResourceParser: MgoResourceParser,
    private val organizationRepository: OrganizationRepository,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
    getDataSetsFromDisk: GetDataSetsFromDisk,
  ) {
    private val dataSets = getDataSetsFromDisk()

    suspend operator fun invoke(
      responses: List<FhirResponse>,
      category: HealthCategoryGroup.HealthCategory,
    ): HealthCategoryScreenViewState.ListItemsState {
      // All the responses that have failed
      val errorResponses = responses.filterIsInstance<FhirResponse.Error>()

      // If any of the responses that failed is due to a user error
      val hasUserError = errorResponses.any { response -> response.type == FhirResponseErrorType.USER }

      // True if all the responses failed
      val allError = responses.all { it is FhirResponse.Error } && responses.isNotEmpty()

      // True if all the responses that are successful have no fhir data in the response
      val allSuccessEmpty = responses.filterIsInstance<FhirResponse.Success>().all { response -> response.isEmpty }

      // All the responses that are successful
      val successResponses = responses.filterIsInstance<FhirResponse.Success>()

      // Map responses to state
      return when {
        allError -> {
          if (hasUserError) {
            HealthCategoryScreenViewState.ListItemsState.Error.UserError
          } else {
            HealthCategoryScreenViewState.ListItemsState.Error.ServerError
          }
        }

        responses.isEmpty() || allSuccessEmpty -> {
          HealthCategoryScreenViewState.ListItemsState.NoData
        }

        else -> {
          mapLoaded(responses = successResponses, category = category)
        }
      }
    }

    private suspend fun mapLoaded(
      responses: List<FhirResponse.Success>,
      category: HealthCategoryGroup.HealthCategory,
    ): HealthCategoryScreenViewState.ListItemsState {
      // Create mgo resources
      val mgoResourcesWithOrganization = responses.flatMap { response -> response.toMgoResourcesWithOrganization() }

      // Store all mgo resources in a store, because we need them in the ui schema screen
      for (mgoResource in mgoResourcesWithOrganization.map { it.mgoResource }) {
        mgoResourceStore.store(mgoResource)
      }

      // Create list items from them to show in the UI
      val listItemGroups = listItemGroupMapper.invoke(category = category, mgoResourcesWithOrganization = mgoResourcesWithOrganization)

      // Return view state
      return HealthCategoryScreenViewState.ListItemsState.Loaded(listItemGroups)
    }

    private suspend fun FhirResponse.Success.toMgoResourcesWithOrganization(): List<MgoResourceWithOrganization> {
      // Get the data set that belongs to this response
      val dataSet = dataSets.firstOrNull { dataSet -> dataSet.id == request.dataServiceId } ?: return emptyList()

      // Get the organization that belongs to this response
      val organization =
        organizationRepository.getSaved(currentCoroutineContext()).first().firstOrNull { organization -> organization.id == request.organizationId }
          ?: return emptyList()

      // Create the mgo resources
      val mgoResources =
        mgoResourceParser.invoke(
          fhirResponse = mgoByteArrayStorage.get(this.cacheKey)?.toString(Charsets.UTF_8) ?: "{}",
          fhirVersion = dataSet.fhirVersion,
          organizationName = organization.name,
        )
      return mgoResources.map { mgoResource -> MgoResourceWithOrganization(mgoResource = mgoResource, organization = organization) }
    }
  }
