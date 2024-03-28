package nl.rijksoverheid.mgo.data.config.test

import nl.rijksoverheid.mgo.data.config.Config
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.config.TEST_CONFIG
import kotlinx.coroutines.flow.MutableStateFlow

class TestConfigRepository : ConfigRepository {
    private val configFlow = MutableStateFlow(TEST_CONFIG)
    private val configError = MutableStateFlow<Throwable?>(null)

    fun setConfig(config: Config) {
        configFlow.value = config
    }

    fun setError(error: Throwable) {
        configError.value = error
    }

    override suspend fun getConfig(): Result<Config> {
        val config = configFlow.value
        val error = configError.value
        return if (error != null) {
            Result.failure(error)
        } else {
            Result.success(config)
        }
    }
}
