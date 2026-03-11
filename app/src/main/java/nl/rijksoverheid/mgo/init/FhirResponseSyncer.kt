package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FhirResponseSyncer
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val fhirRepository: FhirRepository,
    private val getRequests: GetRequests,
  ) {
    @VisibleForTesting
    var firstSync: Boolean = true

    @VisibleForTesting
    var previousStoredOrganizations: List<MgoOrganization> = listOf()

    operator fun invoke(context: CoroutineContext): Flow<List<MgoOrganization>> =
      organizationRepository.getSaved(context).onEach { organizations ->
        // If any organizations were deleted, also remove the fhir data for it.
        val removedOrganizations = previousStoredOrganizations - organizations.toSet()
        for (organization in removedOrganizations) {
          fhirRepository.delete(organization.id)
        }

        val categories = getHealthCategoriesFromDisk().map { group -> group.categories }.flatten()
        val requests = getRequests(organizations = organizations, categories = categories)
        for (request in requests) {
          fhirRepository.fetch(request = request, forceRefresh = firstSync)
        }

        previousStoredOrganizations = organizations
        firstSync = false
      }
  }
