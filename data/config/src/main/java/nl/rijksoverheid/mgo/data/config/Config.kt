package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse

data class Config(
    val androidMinimumVersion: Int,
    val configTTL: Long,
    val configMinimumIntervalSeconds: Long,
)

internal fun ConfigResponse.toConfig(): Config {
    return Config(
        androidMinimumVersion = androidMinimumVersion,
        configTTL = configTTL,
        configMinimumIntervalSeconds = configMinimumIntervalSeconds,
    )
}

val TEST_CONFIG = Config(androidMinimumVersion = 1, configTTL = 300, configMinimumIntervalSeconds = 60)
