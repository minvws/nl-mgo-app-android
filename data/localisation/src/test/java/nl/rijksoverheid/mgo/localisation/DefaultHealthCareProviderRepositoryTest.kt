package nl.rijksoverheid.mgo.localisation

import nl.rijksoverheid.mgo.data.localisation.DefaultHealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.createApi
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServer
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultHealthCareProviderRepositoryTest {
    private val testServer = TestServer()

    @Test
    fun `Given searchApi request is successful, When calling search, Then return health providers`() =
        runTest {
            // Given
            testServer.start()
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/search.json"))

            // When
            val repository = getRepository()
            val result = repository.search(name = "name", city = "city")

            // Then
            val healthCareProviders = result.getOrNull()
            assertEquals(45, healthCareProviders?.size)

            val expectedFirstHealthProvider =
                HealthCareProvider(
                    id = "12001468",
                    name = "Tandartspraktijk Van Dijck",
                    address = "Ginnekenweg 183\r\n4835NA BREDA",
                    city = "BREDA",
                    postalCode = "4835NA",
                )
            assertEquals(expectedFirstHealthProvider, healthCareProviders?.firstOrNull())
        }

    private fun getRepository(): DefaultHealthCareProviderRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val searchApi = createApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultHealthCareProviderRepository(searchApi = searchApi)
    }
}
