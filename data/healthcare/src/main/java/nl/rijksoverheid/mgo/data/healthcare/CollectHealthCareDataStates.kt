package nl.rijksoverheid.mgo.data.healthcare

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

@Singleton
class CollectHealthCareDataStates
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
        private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
    ) {
        @VisibleForTesting
        var previousStoredOrganizations: List<MgoOrganization> = runBlocking { organizationRepository.get() }

        suspend operator fun invoke() {
            organizationRepository.storedOrganizationsFlow.onEach { organizations ->
                val removedOrganizations = previousStoredOrganizations - organizations

                for (organization in removedOrganizations) {
                    healthCareDataStatesRepository.delete(organization)
                }

                for (organization in organizations) {
                    for (category in HealthCareCategory.entries) {
                        healthCareDataStatesRepository.refresh(organization = organization, category = category)
                    }
                }

                previousStoredOrganizations = organizations
            }.collect()
        }
    }
