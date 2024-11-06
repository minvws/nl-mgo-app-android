package nl.rijksoverheid.mgo.data.healthcare

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class DefaultUiSchemaRepository
    @Inject
    constructor(
        private val uiSchemaMapper: UiSchemaMapper,
        private val dvaApi: DvaApi,
        @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    ) : UiSchemaRepository {
        override suspend fun getUiSchema(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): List<Result<List<UISchema>>> {
            val requests = category.getRequests()
            return requests.mapNotNull { request ->
                val resourceEndpoint =
                    organization.dataServices.firstOrNull { dataService ->
                        dataService.type == request.dataServiceType
                    }?.resourceEndpoint
                if (resourceEndpoint == null) return@mapNotNull null
                val requestResult =
                    executeNetworkRequest {
                        dvaApi.get(
                            resourceEndpoint = resourceEndpoint,
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
                    .onFailure { error ->
                        Timber.e(error, "Failed")
                    }
            }
        }
    }
