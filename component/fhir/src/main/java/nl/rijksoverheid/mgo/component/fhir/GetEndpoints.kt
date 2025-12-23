package nl.rijksoverheid.mgo.component.fhir

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.models.Endpoint
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class GetEndpoints
  @Inject
  constructor(
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
  ) {
    operator fun invoke(
      organizations: List<MgoOrganization>,
      categories: List<HealthCategoryGroup.HealthCategory>,
    ): List<Endpoint> =
      organizations
        .flatMap { organization ->
          categories.map { category ->
            getEndpointsForHealthCategory(category, organization)
          }
        }.flatten()
        .distinctBy { endpoint -> endpoint.endpointPath to endpoint.resourceEndpoint }
  }
