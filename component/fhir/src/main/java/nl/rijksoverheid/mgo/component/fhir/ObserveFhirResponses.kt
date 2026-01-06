package nl.rijksoverheid.mgo.component.fhir

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class ObserveFhirResponses
  @Inject
  constructor(
    private val getEndpoints: GetEndpoints,
    private val fhirRepository: FhirRepository,
  ) {
    operator fun invoke(
      categories: List<HealthCategoryGroup.HealthCategory>,
      organizations: List<MgoOrganization>,
    ): Flow<List<FhirResponse>> {
      // All the endpoints that are requested
      val endpoints = getEndpoints(organizations = organizations, categories = categories)

      // Get the fhir responses
      val fhirResponses =
        fhirRepository.observe().map { responses ->
          responses.filter { response ->
            endpoints.any { endpoint ->
              endpoint.organization.id == response.request.organizationId &&
                endpoint.dataServiceId == response.request.dataServiceId &&
                endpoint.endpointId == response.request.endpointId
            }
          }
        }

      // Return fhir responses
      return fhirResponses
    }
  }
