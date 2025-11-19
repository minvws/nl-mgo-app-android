package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.models.Endpoint
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class GetEndpointsForHealthCategory
  @Inject
  constructor(
    private val getDataSetsFromDisk: GetDataSetsFromDisk,
  ) {
    operator fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      organization: MgoOrganization,
    ): List<Endpoint> {
      val dataSets = getDataSetsFromDisk()
      val profilesForCategory = category.subcategories.map { subcategory -> subcategory.profiles }.flatten()

      return organization.dataServices
        .mapNotNull { dataService ->
          val dataSet = dataSets.firstOrNull { dataSet -> dataSet.id == dataService.id } ?: return@mapNotNull null
          val dataSetEndpoints = dataSet.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
          dataSetEndpoints.map { dataSetEndpoint ->
            Endpoint(
              dataServiceId = dataService.id,
              endpointId = dataSetEndpoint.id,
              endpointPath = dataSetEndpoint.path,
              resourceEndpoint = dataService.resourceEndpoint,
              fhirVersion = dataSet.fhirVersion,
            )
          }
        }.flatten()
    }
  }
