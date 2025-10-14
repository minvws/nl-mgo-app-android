package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
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
      val categories = getHealthCategoriesFromDisk.invoke().map { group -> group.categories }.flatten()
      val dataServices = dataServices.map { dataService -> dataService }
      for (category in categories) {
        val endpointsWithDataSet = getEndpointsForHealthCategory(category = category, filterDataSetIds = dataServices.map { it.id })

        for (endpointWithDataSet in endpointsWithDataSet) {
          for (endpoint in endpointWithDataSet.endpoints) {
            for (dataService in dataServices) {
              fhirRepository.fetch(
                organizationId = id,
                dataServiceId = dataService.id,
                endpointId = endpoint.id,
                resourceEndpoint = dataService.resourceEndpoint,
                fhirVersion = FhirVersion.valueOf(endpointWithDataSet.dataSet.fhirVersion),
                url = "$dvaApiBaseUrl/fhir${endpoint.url}",
                forceRefresh = firstSync,
              )
            }
          }
        }
      }
    }
  }
