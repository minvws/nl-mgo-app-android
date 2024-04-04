package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import nl.rijksoverheid.mgo.framework.environment.AppInfo

sealed class ConfigState {
    data object NoAction : ConfigState()

    data object UpdateRequired : ConfigState()
}

internal fun ConfigResponse.toConfigState(appInfo: AppInfo): ConfigState {
    return when {
        appInfo.versionCode < androidMinimumVersion -> ConfigState.UpdateRequired
        else -> ConfigState.NoAction
    }
}
