package nl.rijksoverheid.mgo.data.config.datasource

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import java.io.FileNotFoundException

internal class TestLocalDataSource : ConfigResponseDataSource {
    private var configResponse: ConfigResponse? = null

    override suspend fun get(): Result<ConfigResponse> {
        val configResponse = this.configResponse
        return if (configResponse == null) {
            Result.failure(FileNotFoundException())
        } else {
            Result.success(configResponse)
        }
    }

    override fun store(response: ConfigResponse) {
        this.configResponse = response
    }
}
