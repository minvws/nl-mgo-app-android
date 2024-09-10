package nl.rijksoverheid.mgo.data.healthcare

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

@Singleton
internal class DefaultHealthCareRepository
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
        private val uiSchemaMapper: UiSchemaMapper,
        private val dvaApi: DvaApi,
    ) : HealthCareRepository {
        private val medications: MutableStateFlow<Map<MgoOrganization, HealthCareData>> = MutableStateFlow(mapOf())

        override suspend fun init() {
            organizationRepository.storedOrganizationsFlow.collectLatest { organizations ->
                for (organization in organizations) {
                    withContext(Dispatchers.IO) {
                        getMedications(organization = organization)
                    }
                }
            }
        }

        private suspend fun getMedications(organization: MgoOrganization) {
            // If our medications are already loaded, we don't need to do it again
            val medicationLoaded = medications.value[organization] is HealthCareData.Loaded
            if (medicationLoaded) return

            // Update UI to show loading state
            updateMedications(data = HealthCareData.Loading, organization = organization)

            // Fetch our medications
            val requestResult = executeNetworkRequest { dvaApi.medicationStatement(organization.resourceEndpoint) }

            // Create ui schemas from request
            val uiSchemaListResult =
                requestResult.mapCatching { responseBody ->
                    uiSchemaMapper.getUiSchema(
                        fhirBundleJson = responseBody.string(),
                        profile = ZibMedicationUseProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationUse.value,
                    )
                }

            // Create health care data object
            val healthCareDataResult =
                uiSchemaListResult.mapCatching { uiSchemaList ->
                    HealthCareData.Loaded(organization = organization, uiSchemaList = uiSchemaList)
                }

            // Update UI if success
            healthCareDataResult.onSuccess { healthCareData ->
                updateMedications(data = healthCareData, organization = organization)
            }

            // Update UI if error
            uiSchemaListResult.onFailure { error ->
                updateMedications(data = HealthCareData.Error(error), organization = organization)
            }
        }

        private fun updateMedications(
            data: HealthCareData,
            organization: MgoOrganization,
        ) {
            val newMedications = medications.value.toMutableMap()
            newMedications[organization] = data
            medications.update { newMedications }
        }

        override fun observeData(category: HealthCareCategory): Flow<List<HealthCareData>> {
            return when (category) {
                HealthCareCategory.MEDICATIONS -> medications.map { it.values.toList() }
                HealthCareCategory.ALLERGIES -> flow { }
                HealthCareCategory.MEASUREMENTS -> flow { }
                HealthCareCategory.VACCINATIONS -> flow { }
                HealthCareCategory.COMPLAINTS -> flow { }
                HealthCareCategory.TREATMENTS -> flow { }
                HealthCareCategory.LABRESULTS -> flow { }
                HealthCareCategory.REPORTS -> flow { }
                HealthCareCategory.DOCUMENTS -> flow { }
            }
        }
    }
