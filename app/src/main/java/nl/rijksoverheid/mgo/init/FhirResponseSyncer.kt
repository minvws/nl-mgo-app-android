package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import javax.inject.Named

class FhirResponseSyncer
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val fhirRepository: FhirRepository,
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
  ) {
    @VisibleForTesting
    var firstSync: Boolean = true

    @VisibleForTesting
    var previousStoredOrganizations: List<MgoOrganization> = listOf()

    operator fun invoke(): Flow<List<MgoOrganization>> =
      organizationRepository.storedOrganizationsFlow.onEach { organizations ->
        // If any organizations were deleted, also remove the fhir data for it.
        val removedOrganizations = previousStoredOrganizations - organizations.toSet()
        for (organization in removedOrganizations) {
          fhirRepository.delete(organization.id)
        }

        // Fetch fhir data for added organizations
        for (organization in organizations) {
          organization.fetchFhirResponses()
        }
        previousStoredOrganizations = organizations
        firstSync = false
      }

    private suspend fun MgoOrganization.fetchFhirResponses() {
      val categories =
        getHealthCategoriesFromDisk
          .invoke()
          .flatMap { it.categories }

      val seenPaths = mutableSetOf<String>()

      for (category in categories) {
        val endpoints =
          getEndpointsForHealthCategory(
            category = category,
            organization = this,
          )

        // Do not do same request twice
        val uniqueEndpoints =
          endpoints.filter { endpoint ->
            seenPaths.add(endpoint.dataServiceId + endpoint.endpointPath)
          }

        for (endpoint in uniqueEndpoints) {
          val request =
            FhirRequest(
              organizationId = id,
              medmijId = medMijId,
              dataServiceId = endpoint.dataServiceId,
              endpointId = endpoint.endpointId,
              endpointPath = endpoint.endpointPath,
              resourceEndpoint = endpoint.resourceEndpoint,
              fhirVersion = endpoint.fhirVersion,
              url = "$dvaApiBaseUrl/fhir${endpoint.endpointPath}",
            )

          fhirRepository.fetch(request = request, forceRefresh = firstSync)
        }
      }
    }
  }
