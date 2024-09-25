package nl.rijksoverheid.mgo.data.healthcare

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class DefaultHealthCareRepository
    @Inject
    constructor(
        private val uiSchemaMapper: UiSchemaMapper,
        private val dvaApi: DvaApi,
        @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    ) : HealthCareRepository {
        override suspend fun getUiSchema(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): List<Result<List<UISchema>>> {
            return organization.dataServices.map { dataService ->
                val requests = dataService.getUrlPaths(category)
                requests.map { request ->
                    val requestResult =
                        executeNetworkRequest {
                            dvaApi.get(
                                resourceEndpoint = dataService.resourceEndpoint,
                                url = "$dvaApiBaseUrl/fhir/${request.urlPath}",
                            )
                        }
                    requestResult
                        .mapCatching { responseBody ->
                            uiSchemaMapper.getUiSchema(
                                fhirBundleJson = responseBody.string(),
                                profile = request.profile,
                            )
                        }
                }
            }.flatten()
        }
    }

private fun MgoOrganizationDataService.getUrlPaths(category: HealthCareCategory): List<HealthCareRequest> {
    return when (category) {
        HealthCareCategory.MEDICATIONS -> {
            when (this) {
                is MgoOrganizationDataService.Bgz -> {
                    listOf(BGZ_MEDICATION_USE, BGZ_MEDICATION_AGREEMENT, BGZ_ADMINISTRATION_AGREEMENT)
                }

                is MgoOrganizationDataService.Gp -> {
                    listOf()
                }
            }
        }
        HealthCareCategory.MEASUREMENTS -> {
            when (this) {
                is MgoOrganizationDataService.Bgz -> listOf()
                is MgoOrganizationDataService.Gp -> listOf()
            }
        }
        HealthCareCategory.LAB_RESULTS -> {
            when (this) {
                is MgoOrganizationDataService.Bgz -> listOf()
                is MgoOrganizationDataService.Gp -> listOf()
            }
        }
        HealthCareCategory.ALLERGIES -> {
            when (this) {
                is MgoOrganizationDataService.Bgz -> listOf(BGZ_ALLERGIES)
                is MgoOrganizationDataService.Gp -> listOf()
            }
        }
        HealthCareCategory.TREATMENTS -> listOf()
        HealthCareCategory.APPOINTMENTS -> listOf()
        HealthCareCategory.VACCINATIONS -> listOf()
        HealthCareCategory.DOCUMENTS -> listOf()
        HealthCareCategory.COMPLAINTS ->
            when (this) {
                is MgoOrganizationDataService.Bgz -> listOf(BGZ_PROBLEM)
                is MgoOrganizationDataService.Gp -> listOf()
            }
        HealthCareCategory.PATIENT -> listOf()
        HealthCareCategory.ALERTS -> listOf()
        HealthCareCategory.PAYMENT -> listOf()
        HealthCareCategory.PLANS -> listOf()
        HealthCareCategory.DEVICES -> listOf()
        HealthCareCategory.MENTAL -> listOf()
        HealthCareCategory.LIFESTYLE ->
            when (this) {
                is MgoOrganizationDataService.Bgz ->
                    listOf(
                        BGZ_LIVING_SITUATION,
                        BGZ_DRUGS_USE,
                        BGZ_ALCOHOL_USE,
                        BGZ_TABACCO_USE,
                        BGZ_NUTRITION_USE,
                    )
                is MgoOrganizationDataService.Gp -> listOf()
            }
    }
}
