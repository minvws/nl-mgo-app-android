package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ObserveHealthCareDataStates
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
        private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
    ) {
        suspend operator fun invoke() =
            coroutineScope {
                organizationRepository.storedOrganizationsFlow.collectLatest { organizations ->
                    for (organization in organizations) {
                        for (category in HealthCareCategory.entries) {
                            launch(Dispatchers.IO) {
                                healthCareDataStatesRepository.refresh(organization = organization, category = category)
                            }
                        }
                    }
                }
            }
    }
