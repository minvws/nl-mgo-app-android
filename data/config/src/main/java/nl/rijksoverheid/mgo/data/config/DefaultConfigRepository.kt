package nl.rijksoverheid.mgo.data.config

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import javax.inject.Inject
import kotlinx.coroutines.delay

internal class DefaultConfigRepository
    @Inject
    constructor(private val configApi: ConfigApi) : ConfigRepository {
        override suspend fun getConfig(): Result<Config> {
            delay(2000)
            return executeNetworkRequest { configApi.getConfig() }
                .mapCatching { response -> response.toConfig() }
        }
    }
