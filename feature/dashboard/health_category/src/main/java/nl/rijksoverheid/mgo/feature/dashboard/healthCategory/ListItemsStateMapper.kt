package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseErrorType
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroupedByDateMapper
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroupedBySubcategoryMapper
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type.HealthCategoryScreenType
import javax.inject.Inject

internal class ListItemsStateMapper
  @Inject
  constructor(
    private val groupedBySubcategoryMapper: ListItemsGroupedBySubcategoryMapper,
    private val groupedByDateMapper: ListItemsGroupedByDateMapper,
  ) {
    suspend operator fun invoke(
      responses: List<FhirResponse>,
      mgoResources: List<MgoResource>,
      category: HealthCategoryGroup.HealthCategory,
      type: HealthCategoryScreenType,
    ): HealthCategoryScreenViewState.ListItemsState {
      // All the responses that have failed
      val errorResponses = responses.filterIsInstance<FhirResponse.Error>()

      // If any of the responses that failed is due to a user error
      val hasUserError = errorResponses.any { response -> response.type == FhirResponseErrorType.USER }

      // True if all the responses failed
      val allError = responses.all { it is FhirResponse.Error } && responses.isNotEmpty()

      // True if all the responses that are successful have no fhir data in the response
      val allSuccessEmpty = responses.filterIsInstance<FhirResponse.Success>().all { response -> response.isEmpty }

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
          val listItemGroups =
            when (type) {
              HealthCategoryScreenType.DATE -> groupedByDateMapper(mgoResources)
              HealthCategoryScreenType.SUBCATEGORY -> groupedBySubcategoryMapper(category = category, mgoResources = mgoResources)
            }
          HealthCategoryScreenViewState.ListItemsState.Loaded(listItemGroups)
        }
      }
    }
  }
