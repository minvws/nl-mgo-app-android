package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseErrorType
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
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
      mgoResources: List<MgoResource>,
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
          val listItemGroups = listItemGroupMapper.invoke(category = category, mgoResources = mgoResources)
          HealthCategoryScreenViewState.ListItemsState.Loaded(listItemGroups)
        }
      }
    }
  }
