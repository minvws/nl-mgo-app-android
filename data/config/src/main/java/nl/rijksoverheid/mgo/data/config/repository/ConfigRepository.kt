package nl.rijksoverheid.mgo.data.config.repository

import nl.rijksoverheid.mgo.data.config.Config

interface ConfigRepository {
    suspend fun getConfig(): Result<Config>
}
