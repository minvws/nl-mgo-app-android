package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Entry point to start fetching health care data based on the organizations you added.
 *
 * @param organizationRepository The [OrganizationRepository] to observe organizations that are added or removed.
 * @param healthCareDataStatesRepository The [HealthCareDataStatesRepository] to get or remove health care data based on the added
 * organizations.
 */
@Singleton
class CollectHealthCareDataStates
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
  ) {
    @VisibleForTesting
    var previousStoredOrganizations: List<MgoOrganization> = runBlocking { organizationRepository.get() }

    operator fun invoke(): Flow<List<MgoOrganization>> {
      return organizationRepository.storedOrganizationsFlow.onEach { organizations ->
        val removedOrganizations = previousStoredOrganizations - organizations.toSet()

        for (organization in removedOrganizations) {
          healthCareDataStatesRepository.delete(organization)
        }

        for (category in HealthCareCategory.entries) {
          for (organization in organizations) {
            healthCareDataStatesRepository.refresh(organization = organization, category = category)
          }
        }

        previousStoredOrganizations = organizations
      }
    }
  }
