package nl.rijksoverheid.mgo.data.config.datasource

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.config.api.ConfigApi
import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import javax.inject.Inject

internal class ConfigResponseRemoteDataSource
    @Inject
    constructor(
        private val configApi: ConfigApi,
    ) : ConfigResponseDataSource {
        override suspend fun get(): Result<ConfigResponse> {
            return executeNetworkRequest { configApi.getConfig() }
        }

        override fun store(response: ConfigResponse) {
        }
    }
