package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class DefaultHealthCareStateRepository
    @Inject
    constructor(
        private val healthCareRepository: HealthCareRepository,
        private val organizationRepository: OrganizationRepository,
    ) : HealthCareStateRepository {
        private val statesFlow: MutableStateFlow<List<HealthCareDataState>> =
            MutableStateFlow(
                listOf(),
            )

        override suspend fun init() =
            coroutineScope {
                // When something changes in the organizations (adding, changing, removing)
                organizationRepository.storedOrganizationsFlow.collectLatest { organizations ->
                    // Clear all states
                    statesFlow.update { listOf() }

                    // Set loading states
                    for (organization in organizations) {
                        for (category in HealthCareCategory.entries) {
                            setLoading(organization = organization, category = category)
                        }
                    }

                    // Load data
                    for (organization in organizations) {
                        for (category in HealthCareCategory.entries) {
                            get(organization = organization, category = category)
                        }
                    }
                }
            }

        override suspend fun observe(
            category: HealthCareCategory,
            organization: MgoOrganization?,
        ): Flow<List<HealthCareDataState>> {
            return if (organization == null) {
                statesFlow.map { states ->
                    states.filter { it.category == category }
                }
            } else {
                statesFlow.map { states ->
                    states.filter { it.category == category }.filter { it.organization == organization }
                }
            }
        }

        private fun setLoading(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ) {
            val state =
                HealthCareDataState(
                    loading = true,
                    organization = organization,
                    category = category,
                    uiSchemaListResults = listOf(),
                )
            statesFlow.update { states -> states.toMutableList().also { it.add(state) } }
        }

        private suspend fun get(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ) {
            val uiSchemaListResults = healthCareRepository.getUiSchema(organization = organization, category = category)
            Timber.v("Ik kom hier: " + uiSchemaListResults.map { it.getOrNull() }.count())
            val newState =
                HealthCareDataState(
                    loading = false,
                    organization = organization,
                    category = category,
                    uiSchemaListResults = uiSchemaListResults,
                )
            statesFlow.update { states ->
                states.map { state ->
                    if (state.organization == organization && state.category == category) {
                        newState
                    } else {
                        state
                    }
                }
            }
        }
    }
