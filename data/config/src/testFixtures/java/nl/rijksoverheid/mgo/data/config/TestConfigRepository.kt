package nl.rijksoverheid.mgo.data.config

import kotlinx.coroutines.flow.MutableStateFlow

class TestConfigRepository(initialConfigState: ConfigState = ConfigState.NoAction) : ConfigRepository {
    private val flow = MutableStateFlow(initialConfigState)
    private var configState: ConfigState = initialConfigState

    override val configStateFlow: MutableStateFlow<ConfigState> = flow

    fun setConfigState(configState: ConfigState) {
        this.configState = configState
    }

    override suspend fun refresh(): Result<ConfigState> {
        flow.emit(configState)
        return Result.success(configState)
    }
}
