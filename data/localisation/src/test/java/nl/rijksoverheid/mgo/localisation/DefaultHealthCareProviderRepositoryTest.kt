package nl.rijksoverheid.mgo.localisation

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.DefaultHealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.createApi
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProviders
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.framework.storage.file.TestFileStore
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import kotlinx.coroutines.test.runTest

internal class DefaultHealthCareProviderRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer
    private val fileStore = TestFileStore()

    @Before
    fun setUp() {
        fileStore.clear()
    }

    @Test
    fun `Given searchApi request is successful, When calling search, Then emit health providers`() =
        runTest {
            // Given
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/search.json"))

            // When
            val repository = getRepository()
            val searchFlow = repository.search(name = "name", city = "city")

            // Then
            searchFlow.test {
                val healthCareProviders = awaitItem()
                assertEquals(45, healthCareProviders.size)

                val expectedFirstHealthProvider =
                    HealthCareProvider(
                        id = "12001468",
                        name = "Tandartspraktijk Van Dijck",
                        address = "Ginnekenweg 183\r\n4835NA BREDA",
                        category = "Tandartsen",
                        added = false,
                    )
                assertEquals(expectedFirstHealthProvider, healthCareProviders.firstOrNull())
            }
        }

    @Test
    fun `Given searchApi request failed, When calling search, Then emit error`() =
        runTest {
            // Given
            testServer.enqueue500()

            // When
            val repository = getRepository()
            val searchFlow = repository.search(name = "name", city = "city")

            // Then
            searchFlow.test {
                val exception = awaitError() as HttpException
                assertEquals(500, exception.code())
            }
        }

    @Test
    fun `Given no health care providers saved, When collecting stored providers flow, Then emit no health care providers`() =
        runTest {
            // Given no providers

            // When
            val repository = getRepository()
            val healthCareProviders = repository.get()

            // Then
            repository.storedHealthCareProvidersFlow.test {
                assertEquals(listOf<HealthCareProvider>(), awaitItem())
            }
        }

    @Test
    fun `Given health care providers saved, When collecting providers flow, Then emit health care providers`() =
        runTest {
            // Given
            val storedHealthCareProviders =
                HealthCareProviders(
                    providers =
                        listOf(
                            TEST_HEALTH_CARE_PROVIDER.copy(id = "1"),
                            TEST_HEALTH_CARE_PROVIDER.copy(id = "2"),
                            TEST_HEALTH_CARE_PROVIDER.copy(id = "3"),
                        ),
                )
            fileStore.saveFile(storedHealthCareProviders, "healthcareproviders.json")

            // When
            val repository = getRepository()

            // Then
            repository.storedHealthCareProvidersFlow.test {
                assertEquals(storedHealthCareProviders.providers, awaitItem())
            }
        }

    @Test
    fun `Given health care provider, When calling save, Then save health care provider to storage`() =
        runTest {
            // Given no providers

            // When
            val provider = TEST_HEALTH_CARE_PROVIDER
            val repository = getRepository()
            repository.save(provider)

            // Then
            repository.storedHealthCareProvidersFlow.test {
                val storedProviders = awaitItem()
                assertEquals(listOf(provider), storedProviders)
            }
        }

    @Test
    fun `Given health care provider, When calling delete, Then delete health care provider from storage`() =
        runTest {
            // Given
            val storedHealthCareProviders =
                HealthCareProviders(
                    providers =
                        listOf(
                            TEST_HEALTH_CARE_PROVIDER.copy(id = "1"),
                            TEST_HEALTH_CARE_PROVIDER.copy(id = "2"),
                            TEST_HEALTH_CARE_PROVIDER.copy(id = "3"),
                        ),
                )
            fileStore.saveFile(storedHealthCareProviders, "healthcareproviders.json")

            // When
            val repository = getRepository()
            repository.delete(storedHealthCareProviders.providers.first())

            // Then
            repository.storedHealthCareProvidersFlow.test {
                val expectedProviders = storedHealthCareProviders.providers.drop(1)
                val storedProviders = awaitItem()
                assertEquals(expectedProviders, storedProviders)
            }
        }

    private fun getRepository(): DefaultHealthCareProviderRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val searchApi = createApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultHealthCareProviderRepository(searchApi = searchApi, fileStore = fileStore)
    }
}
