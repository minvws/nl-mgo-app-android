package nl.rijksoverheid.mgo.data.config

interface ConfigRepository {
    suspend fun getConfig(): Result<Config>
}
