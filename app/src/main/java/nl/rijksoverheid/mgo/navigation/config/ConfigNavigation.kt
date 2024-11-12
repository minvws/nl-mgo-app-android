package nl.rijksoverheid.mgo.navigation.config

import kotlinx.serialization.Serializable

sealed class ConfigNavigation {
    @Serializable
    data object UpdateRequired : ConfigNavigation()
}
