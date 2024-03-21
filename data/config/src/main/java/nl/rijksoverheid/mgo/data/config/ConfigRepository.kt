package nl.rijksoverheid.mgo.data.config

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import javax.inject.Inject
import kotlinx.coroutines.delay

class ConfigRepository
    @Inject
    constructor(private val configApi: ConfigApi) {
        suspend fun getConfig(): Result<Config> {
            delay(2000)
            return executeNetworkRequest { configApi.getConfig() }
        }
    }
