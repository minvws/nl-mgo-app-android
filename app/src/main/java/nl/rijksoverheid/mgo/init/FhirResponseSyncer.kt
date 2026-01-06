package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import nl.rijksoverheid.mgo.component.fhir.FetchEndpoint
import nl.rijksoverheid.mgo.component.fhir.GetEndpoints
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject

class FhirResponseSyncer
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val fhirRepository: FhirRepository,
    private val fetchEndpoint: FetchEndpoint,
    private val getEndpoints: GetEndpoints,
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

        val categories = getHealthCategoriesFromDisk().map { group -> group.categories }.flatten()
        val endpoints = getEndpoints(organizations = organizations, categories = categories)
        for (endpoint in endpoints) {
          fetchEndpoint(endpoint = endpoint, forceRefresh = firstSync)
        }

        previousStoredOrganizations = organizations
        firstSync = false
      }
  }
