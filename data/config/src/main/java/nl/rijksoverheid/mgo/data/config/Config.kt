package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse

data class Config(
    val androidMinimumVersion: Int,
)

internal fun ConfigResponse.toConfig(): Config {
    return Config(
        androidMinimumVersion = androidMinimumVersion,
    )
}

val TEST_CONFIG = Config(androidMinimumVersion = 1)
