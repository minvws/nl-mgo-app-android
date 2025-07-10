package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceProfile
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.HealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator.HealthCareUrlCreator
import okhttp3.ResponseBody
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * Handles various operations on [MgoResource].
 *
 * @param healthCareDataStatesStore The [HealthCareDataStatesStore] to handle storage of [HealthCareDataState].
 * @param dvaApi The [DvaApi] to communicate with the server.
 * @param urlCreator The [HealthCareUrlCreator] to construct the url to communicate with [dvaApi].
 * @param dvaApiBaseUrl The base url of the call to make to the [DvaApi].
 * @param mgoResourceMapper The [MgoResourceMapper] to map [dvaApi] response to [MgoResource].
 */
internal class DefaultMgoResourceRepository
  @Inject
  constructor(
    private val healthCareDataStatesStore: HealthCareDataStatesStore,
    private val dvaApi: DvaApi,
    private val urlCreator: HealthCareUrlCreator,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    private val mgoResourceMapper: MgoResourceMapper,
  ) : MgoResourceRepository {
    /**
     * Fetches health care data from given endpoint and maps to [MgoResource].
     *
     * @param endpoint The endpoint to get the data from.
     * @param request The [HealthCareRequest] used to construct the url.
     * @return [Result] that if successful, contains a list of [MgoResource].
     */
    override suspend fun get(
      endpoint: String,
      request: HealthCareRequest,
    ): Result<List<MgoResource>> =
      executeRequest(request = request, endpoint = endpoint)
        .mapCatching { requestBody -> requestBody.toMgoResource(request) }
        .onFailure { error -> Timber.e(error, "Failed to fetch health care data") }

    /**
     * Get health care data from the store.
     *
     * @param referenceId The [MgoResourceReferenceId] for the [MgoResource] to get.
     * @return [Result] that if successful ([MgoResource] exists in store), returns the [MgoResource].
     */
    override suspend fun get(referenceId: MgoResourceReferenceId): Result<MgoResource> {
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
            mgoResource.referenceId == referenceId
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
     * Filters a list of [MgoResource] based on the [MgoResourceProfile].
     *
     * @param resources A list of [MgoResource]. Will be filtered to contain only the provided [MgoResourceProfile].
     * @param profiles A list of [MgoResourceProfile].
     * @return The filtered list of [MgoResource].
     */
    override suspend fun filter(
      resources: List<MgoResource>,
      profiles: List<MgoResourceProfile>,
    ): List<MgoResource> =
      resources.filter { resource ->
        profiles.contains(resource.profile)
      }

    private suspend fun executeRequest(
      request: HealthCareRequest,
      endpoint: String,
    ): Result<ResponseBody> =
      executeNetworkRequest {
        dvaApi.get(
          resourceEndpoint = endpoint,
          url = urlCreator.invoke(baseUrl = "$dvaApiBaseUrl/fhir/${request.path}", request = request),
          accept = "application/fhir+json; fhirVersion=${request.fhirVersion.versionNumber}",
        )
      }

    private suspend fun ResponseBody.toMgoResource(request: HealthCareRequest): List<MgoResource> =
      mgoResourceMapper.get(
        fhirBundleJson = string(),
        fhirVersion = request.fhirVersion,
      )
  }
