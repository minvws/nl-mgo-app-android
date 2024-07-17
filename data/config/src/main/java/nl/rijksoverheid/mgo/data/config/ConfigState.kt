package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import nl.rijksoverheid.mgo.framework.environment.Environment

sealed class ConfigState {
    data object NoAction : ConfigState()

    data object UpdateRequired : ConfigState()
}

internal fun ConfigResponse.toConfigState(environment: Environment): ConfigState {
    return when {
        environment.versionCode < androidMinimumVersion -> ConfigState.UpdateRequired
        else -> ConfigState.NoAction
    }
}
