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
    private val getRequests: GetRequests,
    private val fhirRepository: FhirRepository,
  ) {
    operator fun invoke(
      categories: List<HealthCategoryGroup.HealthCategory>,
      organizations: List<MgoOrganization>,
    ): Flow<List<FhirResponse>> {
      // All the endpoints that are requested
      val requests = getRequests(organizations = organizations, categories = categories)

      // Get the fhir responses
      val fhirResponses =
        fhirRepository.observe().map { responses ->
          responses.filter { response ->
            requests.contains(response.request)
          }
        }

      // Return fhir responses
      return fhirResponses
    }
  }
