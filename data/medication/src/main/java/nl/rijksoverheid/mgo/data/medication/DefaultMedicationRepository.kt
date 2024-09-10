package nl.rijksoverheid.mgo.data.medication

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheCategory
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheKey
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaRepository
import javax.inject.Inject

internal class DefaultMedicationRepository
    @Inject
    constructor(
        private val dvaApi: DvaApi,
        private val uiSchemaMapper: UiSchemaMapper,
        private val uiSchemaRepository: UiSchemaRepository,
    ) :
    MedicationRepository {
        override suspend fun getMedications(
            organizationId: String,
            resourceEndpoint: String,
        ): Result<List<UISchema>> {
            val result = executeNetworkRequest { dvaApi.medicationStatement(resourceEndpoint) }
            return result
                .mapCatching { responseBody ->
                    uiSchemaMapper.getUiSchema(
                        fhirBundleJson = responseBody.string(),
                        profile = ZibMedicationUseProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationUse.value,
                    )
                }
                .onSuccess { uiSchemaList ->
                    val cacheKey = UiSchemaCacheKey(organizationId = organizationId, category = UiSchemaCacheCategory.MEDICATION_USE)
                    uiSchemaRepository.store(cacheKey, uiSchemaList)
                }
        }
    }
