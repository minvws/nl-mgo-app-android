package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import nl.rijksoverheid.mgo.data.config.datasource.TestLocalDataSource
import nl.rijksoverheid.mgo.data.config.repository.DefaultConfigRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultConfigRepositoryTest {
    private val localDataSource = TestLocalDataSource()
    private val remoteDataSource = TestLocalDataSource()
    private val repository = DefaultConfigRepository(localDataSource = localDataSource, remoteDataSource = remoteDataSource)

    @Test
    fun `Given remote data source has a config, When getting the config file, Then return Config object`() =
        runTest {
            // Given
            remoteDataSource.store(response = getConfigResponse())

            // When
            val result = repository.getConfig()

            // Then
            val expectedConfig = Config(androidMinimumVersion = 1, configTTL = 300, configMinimumIntervalSeconds = 60)
            assertEquals(Result.success(expectedConfig), result)
        }

    @Test
    fun `Given remote data source has a config, When getting the config file, Then store the config response in the local data source`() =
        runTest {
            // Given
            remoteDataSource.store(response = getConfigResponse())

            // When
            repository.getConfig()

            // Then
            assertEquals(Result.success(getConfigResponse()), localDataSource.get())
        }

    @Test
    fun `Given local data source has a config, When getting the config file, Then return Config object`() =
        runTest {
            // Given
            localDataSource.store(response = getConfigResponse())

            // When
            val result = repository.getConfig()

            // Then
            val expectedConfig = Config(androidMinimumVersion = 1, configTTL = 300, configMinimumIntervalSeconds = 60)
            assertEquals(Result.success(expectedConfig), result)
        }

    @Test
    fun `Given local data source has a config, When getting the config file, No call to remote config is done`() =
        runTest {
            // Given
            localDataSource.store(response = getConfigResponse())
            remoteDataSource.store(response = getConfigResponse(androidMinimumVersion = 2))

            // When
            val result = repository.getConfig()

            // Then
            val expectedConfig = Config(androidMinimumVersion = 1, configTTL = 300, configMinimumIntervalSeconds = 60)
            assertEquals(Result.success(expectedConfig), result)
        }

    private fun getConfigResponse(androidMinimumVersion: Int = 1): ConfigResponse {
        return ConfigResponse(
            androidMinimumVersion = androidMinimumVersion,
            configTTL = 300L,
            configMinimumIntervalSeconds = 60L,
        )
    }
}
