package nl.rijksoverheid.mgo.data.healthcare.healthCareData

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.urlCreator.HealthCareUrlCreator
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import okhttp3.ResponseBody
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

internal class DefaultHealthCareDataRepository
    @Inject
    constructor(
        private val dvaApi: DvaApi,
        private val urlCreator: HealthCareUrlCreator,
        @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
        private val mgoResourceRepository: MgoResourceRepository,
    ) : HealthCareDataRepository {
        override suspend fun get(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): List<Result<List<MgoResourceJson>>> {
            val requests = category.getRequests()
            return requests.mapNotNull { request ->
                organization
                    .executeRequest(request)
                    ?.mapCatching { requestBody -> requestBody.toMgoResource(request) }
                    ?.onFailure { error -> Timber.e(error, "Failed to fetch health care data") }
            }
        }

        private suspend fun MgoOrganization.executeRequest(request: HealthCareRequest): Result<ResponseBody>? {
            val endpoint =
                dataServices.firstOrNull { dataService ->
                    dataService.type == request.dataServiceType
                }?.resourceEndpoint ?: return null

            return executeNetworkRequest {
                dvaApi.get(
                    resourceEndpoint = endpoint,
                    url = urlCreator.invoke(baseUrl = "$dvaApiBaseUrl/fhir/${request.path}", request = request),
                )
            }
        }

        private suspend fun ResponseBody.toMgoResource(request: HealthCareRequest): List<MgoResourceJson> {
            return mgoResourceRepository.get(
                fhirBundleJson = string(),
                fhirVersion = request.fhirVersion,
            )
        }
    }
