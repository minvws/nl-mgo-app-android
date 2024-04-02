package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import nl.rijksoverheid.mgo.data.config.datasource.TestLocalDataSource
import nl.rijksoverheid.mgo.data.config.repository.DefaultConfigRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException
import kotlinx.coroutines.test.runTest

internal class DefaultConfigRepositoryTest {
    private lateinit var localDataSource: TestLocalDataSource
    private lateinit var remoteDataSource: TestLocalDataSource
    private lateinit var repository: DefaultConfigRepository

    private val localConfigResponse = getConfigResponse(androidMinimumVersion = 1)
    private val remoteConfigResponse = getConfigResponse(androidMinimumVersion = 2)

    @Before
    fun setUp() {
        localDataSource = TestLocalDataSource()
        remoteDataSource = TestLocalDataSource()
        repository = DefaultConfigRepository(localDataSource = localDataSource, remoteDataSource = remoteDataSource)
    }

    @Test
    fun `Given local data source has a config, When getting the config file, No call to remote config is done`() =
        runTest {
            // Given
            localDataSource.store(response = localConfigResponse)
            remoteDataSource.store(response = remoteConfigResponse)

            // When
            val result = repository.getConfig()

            // Then
            val expectedConfig = localConfigResponse.toConfig()
            assertEquals(Result.success(expectedConfig), result)
        }

    @Test
    fun `Given remote data source has a config, When getting the config file, Then return Config object`() =
        runTest {
            // Given
            remoteDataSource.store(response = remoteConfigResponse)

            // When
            val result = repository.getConfig()

            // Then
            val expectedConfig = remoteConfigResponse.toConfig()
            assertEquals(Result.success(expectedConfig), result)
        }

    @Test
    fun `Given remote data source has a config, When getting the config file, Then store the config response in the local data source`() =
        runTest {
            // Given
            remoteDataSource.store(response = remoteConfigResponse)

            // When
            repository.getConfig()

            // Then
            assertEquals(Result.success(remoteConfigResponse), localDataSource.get())
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
    fun `Given remote and local data source have no configs , When getting the config file, Then return error`() =
        runTest {
            // Given nothing is set

            // When
            val result = repository.getConfig()

            // Then
            assertTrue(result.exceptionOrNull() is FileNotFoundException)
        }

    private fun getConfigResponse(androidMinimumVersion: Int = 1): ConfigResponse {
        return ConfigResponse(
            androidMinimumVersion = androidMinimumVersion,
            configTTL = 300L,
            configMinimumIntervalSeconds = 60L,
        )
    }
}
