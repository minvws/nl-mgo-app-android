package nl.rijksoverheid.mgo.data.config

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.config.api.ConfigApi
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
internal class DefaultConfigRepository(
    private val appInfo: AppInfo,
    private val configApi: ConfigApi,
) : ConfigRepository {
    override val configStateFlow: MutableStateFlow<ConfigState> = MutableStateFlow(ConfigState.NoAction)

    override suspend fun refresh(): Result<ConfigState> {
        val result = executeNetworkRequest { configApi.getConfig() }
        return result
            .mapCatching { response -> response.toConfigState(appInfo) }
            .onSuccess { configState -> configStateFlow.value = configState }
    }
}
