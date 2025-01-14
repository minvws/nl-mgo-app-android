package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

/**
 * Entry point to fetch data for all stored health care providers. Listens to changes in stored health care providers
 * and updates the health care data in [HealthCareDataStatesRepository].
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
