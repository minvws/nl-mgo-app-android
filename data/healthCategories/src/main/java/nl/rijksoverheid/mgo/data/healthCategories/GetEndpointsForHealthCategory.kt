package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.EndpointsWithDataSet
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class GetEndpointsForHealthCategory
  @Inject
  constructor(
    private val getDataSetsFromDisk: GetDataSetsFromDisk,
  ) {
    operator fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      filterDataSetIds: List<String>,
    ): List<EndpointsWithDataSet> {
      val dataSets = getDataSetsFromDisk().filter { dataSet -> filterDataSetIds.contains(dataSet.id) }
      val profilesForCategory = category.subcategories.map { subcategory -> subcategory.profiles }.flatten()
      return dataSets
        .map { dataSet ->
          val endpoints = dataSet.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
          EndpointsWithDataSet(
            dataSet = dataSet,
            endpoints = endpoints,
          )
        }.filter { it.endpoints.isNotEmpty() }
    }
  }
