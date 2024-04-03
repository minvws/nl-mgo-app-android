package nl.rijksoverheid.mgo.data.config

import kotlinx.coroutines.flow.MutableStateFlow

interface ConfigRepository {
    val configStateFlow: MutableStateFlow<ConfigState>

    suspend fun refresh(): Result<ConfigState>
}
