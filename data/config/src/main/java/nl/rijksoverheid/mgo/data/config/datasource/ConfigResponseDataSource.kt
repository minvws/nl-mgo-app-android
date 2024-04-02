package nl.rijksoverheid.mgo.data.config.datasource

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse

internal interface ConfigResponseDataSource {
    suspend fun get(): Result<ConfigResponse>
}
