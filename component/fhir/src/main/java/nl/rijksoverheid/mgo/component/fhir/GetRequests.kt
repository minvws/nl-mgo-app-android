package nl.rijksoverheid.mgo.component.fhir

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class GetRequests
  @Inject
  constructor(
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
  ) {
    operator fun invoke(
      organizations: List<MgoOrganization>,
      categories: List<HealthCategoryGroup.HealthCategory>,
    ): List<FhirRequest> =
      organizations
        .flatMap { organization ->
          categories.map { category ->
            getEndpointsForHealthCategory(category, organization)
          }
        }.flatten()
        .distinctBy { endpoint -> endpoint.endpointPath to endpoint.resourceEndpoint }
        .map { endpoint -> endpoint.toFhirRequest() }
  }
