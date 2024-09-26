package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ObserveHealthCareDataStates
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
        private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
    ) {
        suspend operator fun invoke(): Flow<Unit> =
            coroutineScope {
                organizationRepository.storedOrganizationsFlow.onEach { organizations ->
                    for (organization in organizations) {
                        for (category in HealthCareCategory.entries) {
                            healthCareDataStatesRepository.refresh(organization = organization, category = category)
                        }
                    }
                }.map { }
            }
    }
