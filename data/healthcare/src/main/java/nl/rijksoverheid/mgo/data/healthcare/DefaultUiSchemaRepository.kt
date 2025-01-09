package nl.rijksoverheid.mgo.data.healthcare

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.healthcare.util.HealthCareUrlCreator
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.HealthCareResourceMapper
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class DefaultUiSchemaRepository
    @Inject
    constructor(
        private val healthCareResourceMapper: HealthCareResourceMapper,
        private val dvaApi: DvaApi,
        private val urlCreator: HealthCareUrlCreator,
        @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    ) : UiSchemaRepository {
        override suspend fun getUiSchema(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): List<Result<List<String>>> {
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
                            url = urlCreator.invoke(baseUrl = "${dvaApiBaseUrl}fhir/${request.path}", request = request),
                        )
                    }
                requestResult
                    .mapCatching { responseBody ->
                        healthCareResourceMapper.getResources(
                            fhirBundleJson = responseBody.string(),
                            fhirVersion = request.fhirVersion,
                        )
                    }
                    .onFailure { error ->
                        Timber.e(error, "Failed")
                    }
            }
        }
    }
