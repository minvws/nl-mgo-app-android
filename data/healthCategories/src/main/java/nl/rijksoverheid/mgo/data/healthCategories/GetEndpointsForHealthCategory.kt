package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.EndpointsWithDataSetId
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class GetEndpointsForHealthCategory
  @Inject
  constructor(
    private val getDataSetsFromDisk: GetDataSetsFromDisk,
  ) {
    operator fun invoke(category: HealthCategoryGroup.HealthCategory): List<EndpointsWithDataSetId> {
      val dataSets = getDataSetsFromDisk()
      val profilesForCategory = category.subcategories.map { subcategory -> subcategory.profiles }.flatten()
      return dataSets
        .map { dataSet ->
          val endpoints = dataSet.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
          EndpointsWithDataSetId(
            id = dataSet.id,
            endpoints = endpoints,
          )
        }.filter { it.endpoints.isNotEmpty() }
    }
  }
