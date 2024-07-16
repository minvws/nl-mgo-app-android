package nl.rijksoverheid.mgo.localisation

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.api.load.createLoadApi
import nl.rijksoverheid.mgo.data.localisation.DefaultOrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizations
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.storage.file.TestFileStore
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import kotlinx.coroutines.test.runTest

internal class DefaultMgoOrganizationRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer
    private val fileStore = TestFileStore()

    @Before
    fun setUp() {
        fileStore.clear()
    }

    @Test
    fun `Given loadApi request is successful, When calling search, Then emit health providers`() =
        runTest {
            // Given
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/search.json"))

            // When
            val repository = getRepository()
            val searchFlow = repository.search(name = "name", city = "city")

            // Then
            searchFlow.test {
                val providers = awaitItem()
                assertEquals(45, providers.size)

                val expectedFirstHealthProvider =
                    MgoOrganization(
                        id = "12001468",
                        name = "Tandartspraktijk Van Dijck",
                        address = "Ginnekenweg 183\r\n4835NA BREDA",
                        category = "Tandartsen",
                        added = false,
                        resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/48",
                    )
                assertEquals(expectedFirstHealthProvider, providers.firstOrNull())
            }
        }

    @Test
    fun `Given loadApi request failed, When calling search, Then emit error`() =
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
            repository.get()

            // Then
            repository.storedOrganizationsFlow.test {
                assertEquals(listOf<MgoOrganization>(), awaitItem())
            }
        }

    @Test
    fun `Given health care providers saved, When collecting providers flow, Then emit health care providers`() =
        runTest {
            // Given
            val storedMgoOrganizations =
                MgoOrganizations(
                    providers =
                        listOf(
                            TEST_MGO_ORGANIZATION.copy(id = "1"),
                            TEST_MGO_ORGANIZATION.copy(id = "2"),
                            TEST_MGO_ORGANIZATION.copy(id = "3"),
                        ),
                )
            fileStore.saveFile(storedMgoOrganizations, "organizations.json")

            // When
            val repository = getRepository()

            // Then
            repository.storedOrganizationsFlow.test {
                assertEquals(storedMgoOrganizations.providers, awaitItem())
            }
        }

    @Test
    fun `Given health care provider, When calling save, Then save health care provider to storage`() =
        runTest {
            // Given no providers

            // When
            val provider = TEST_MGO_ORGANIZATION
            val repository = getRepository()
            repository.save(provider)

            // Then
            repository.storedOrganizationsFlow.test {
                val storedProviders = awaitItem()
                assertEquals(listOf(provider), storedProviders)
            }
        }

    @Test
    fun `Given health care provider, When calling delete, Then delete health care provider from storage`() =
        runTest {
            // Given
            val storedMgoOrganizations =
                MgoOrganizations(
                    providers =
                        listOf(
                            TEST_MGO_ORGANIZATION.copy(id = "1"),
                            TEST_MGO_ORGANIZATION.copy(id = "2"),
                            TEST_MGO_ORGANIZATION.copy(id = "3"),
                        ),
                )
            fileStore.saveFile(storedMgoOrganizations, "organizations.json")

            // When
            val repository = getRepository()
            repository.delete(storedMgoOrganizations.providers.first().id)

            // Then
            repository.storedOrganizationsFlow.test {
                val expectedProviders = storedMgoOrganizations.providers.drop(1)
                val storedProviders = awaitItem()
                assertEquals(expectedProviders, storedProviders)
            }
        }

    private fun getRepository(): DefaultOrganizationRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val loadApi = createLoadApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultOrganizationRepository(loadApi = loadApi, fileStore = fileStore)
    }
}
