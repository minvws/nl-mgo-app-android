package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

internal class ListItemStateMapper
  @Inject
  constructor(
    private val listItemGroupMapper: ListItemGroupMapper,
    private val mgoResourceStore: MgoResourceStore,
  ) {
    suspend operator fun invoke(
      responses: List<FhirResponse>,
      category: HealthCategoryGroup.HealthCategory,
    ): HealthCategoryScreenViewState.ListItemsState {
      // All the responses that are successful
      val successResponses = responses.filterIsInstance<FhirResponse.Success>()

      // True if all the responses that are successful have no fhir data in the response
      val allEmpty = responses.filterIsInstance<FhirResponse.Success>().all { response -> response.isEmpty }

      return if (responses.isEmpty() || allEmpty) {
        mapNoData()
      } else {
        mapLoaded(responses = successResponses, category = category)
      }
    }

    private fun mapNoData(): HealthCategoryScreenViewState.ListItemsState = HealthCategoryScreenViewState.ListItemsState.NoData

    private suspend fun mapLoaded(
      responses: List<FhirResponse.Success>,
      category: HealthCategoryGroup.HealthCategory,
    ): HealthCategoryScreenViewState.ListItemsState {
      // Create list items from them to show in the UI
      val listItemGroups = listItemGroupMapper.invoke(category = category, fhirResponses = responses)

      // Store all mgo resources in a store, because we need them in the ui schema screen
      val mgoResources = listItemGroups.map { group -> group.items.map { item -> item.mgoResource } }.flatten()
      for (mgoResource in mgoResources) {
        mgoResourceStore.store(mgoResource)
      }

      // Return view state
      return HealthCategoryScreenViewState.ListItemsState.Loaded(listItemGroups)
    }
  }
