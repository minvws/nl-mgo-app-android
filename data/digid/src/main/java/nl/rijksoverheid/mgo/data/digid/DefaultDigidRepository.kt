package nl.rijksoverheid.mgo.data.digid

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.vad.StartRequestBody
import nl.rijksoverheid.mgo.data.api.vad.VadApi
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject

/**
 * Handles authenticating with DigiD.
 *
 * @param vadApi The [VadApi] to communicate with the server.
 * @param environmentRepository The [EnvironmentRepository] to get the deeplink host from.
 */
internal class DefaultDigidRepository
  @Inject
  constructor(
    private val vadApi: VadApi,
    private val environmentRepository: EnvironmentRepository,
  ) : DigidRepository {
    /**
     * Start the authentication process with DigiD.
     *
     * @return [Result] containing the authentication URL if the request is successful,
     * or an error if the process fails.
     */
    override suspend fun login(): Result<String> {
      val environment = environmentRepository.getEnvironment()
      val result =
        executeNetworkRequest {
          vadApi.start(
            StartRequestBody("${environment.deeplinkHost}://app/login"),
          )
        }
      return result.mapCatching { response -> response.authUrl }
    }
  }
