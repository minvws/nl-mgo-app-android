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
                    listOf(GP_MEDICATION_AGREEMENT)
                }
            }
        }

        HealthCareCategory.ALLERGIES -> listOf()
        HealthCareCategory.MEASUREMENTS -> listOf()
        HealthCareCategory.VACCINATIONS -> listOf()
        HealthCareCategory.COMPLAINTS -> listOf()
        HealthCareCategory.TREATMENTS -> listOf()
        HealthCareCategory.LABRESULTS -> listOf()
        HealthCareCategory.REPORTS -> listOf()
        HealthCareCategory.DOCUMENTS -> listOf()
    }
}
