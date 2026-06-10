package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import kotlinx.coroutines.flow.first
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

internal class RetryFailedRequests
  @Inject
  constructor(
    private val getRequests: GetRequests,
    private val fhirRepository: FhirRepository,
  ) {
    suspend operator fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      organizations: List<MgoOrganization>,
    ) {
      // Get requests
      val requests = getRequests(organizations = organizations, categories = listOf(category))

      // Get responses that failed
      val failedResponses =
        fhirRepository
          .observe()
          .first()
          .filterIsInstance<FhirResponse.Error>()
          .filter { response -> requests.contains(response.request) }

      // Map to requests
      val failedRequests = failedResponses.map { response -> response.request }

      // Retry
      fhirRepository.retry(failedRequests)
    }
  }
