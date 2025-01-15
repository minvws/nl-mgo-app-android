package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.HealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator.HealthCareUrlCreator
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import okhttp3.ResponseBody
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

internal class DefaultMgoResourceRepository
    @Inject
    constructor(
        private val healthCareDataStatesStore: HealthCareDataStatesStore,
        private val dvaApi: DvaApi,
        private val urlCreator: HealthCareUrlCreator,
        @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
        private val mgoResourceMapper: MgoResourceMapper,
    ) : MgoResourceRepository {
        override suspend fun get(
            endpoint: String,
            request: HealthCareRequest,
            organization: MgoOrganization,
        ): Result<List<MgoResourceJson>> {
            return executeRequest(request = request, endpoint = endpoint)
                .mapCatching { requestBody -> requestBody.toMgoResource(request) }
                .onFailure { error -> Timber.e(error, "Failed to fetch health care data") }
        }

        override suspend fun get(referenceId: String): Result<MgoResourceJson> {
            val states = healthCareDataStatesStore.get()
            val mgoResource =
                states
                    .asSequence()
                    .filterIsInstance<HealthCareDataState.Loaded>()
                    .map { state -> state.results }
                    .flatten()
                    .mapNotNull { result -> result.getOrNull() }
                    .flatten()
                    .firstOrNull { mgoResource ->
                        val json = JSONObject(mgoResource)
                        json.get("referenceId") == referenceId
                    }
            return if (mgoResource == null) {
                // Currently we only support getting mgo resources when it is already fetched before.
                // There is currently no need, but we can improve this by fetching the mgo resource with the referenceId
                Result.failure(IllegalStateException("Mgo resource is not cached"))
            } else {
                Result.success(mgoResource)
            }
        }

        /**
         * Filters mgo resources based on the profile property.
         * @param resources The mgo resources to filter.
         * @param profiles If the mgo resource contains a profile that exists in this array, it will be returned.
         */
        override suspend fun filter(
            resources: List<MgoResourceJson>,
            profiles: List<String>,
        ): List<MgoResourceJson> {
            return resources.filter { resource ->
                val resourceJsonObject = JSONObject(resource)
                profiles.contains(resourceJsonObject.getString("profile"))
            }
        }

        private suspend fun executeRequest(
            request: HealthCareRequest,
            endpoint: String,
        ): Result<ResponseBody> {
            return executeNetworkRequest {
                dvaApi.get(
                    resourceEndpoint = endpoint,
                    url = urlCreator.invoke(baseUrl = "$dvaApiBaseUrl/fhir/${request.path}", request = request),
                )
            }
        }

        private suspend fun ResponseBody.toMgoResource(request: HealthCareRequest): List<MgoResourceJson> {
            return mgoResourceMapper.get(
                fhirBundleJson = string(),
                fhirVersion = request.fhirVersion,
            )
        }
    }
