package nl.rijksoverheid.mgo.data.localisation

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.api.load.createLoadApi
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizations
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestEncryptedFileStore
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

internal class DefaultOrganizationRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val testServer = testServerRule.testServer
  private val fileStore = TestEncryptedFileStore()

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
            id = "agb-z:12001468",
            name = "Tandartspraktijk Van Dijck",
            address = "Ginnekenweg 183\r\n4835NA BREDA",
            category = "Tandartsen",
            added = false,
            dataServices =
              listOf(
                MgoOrganizationDataService(
                  resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/51",
                  type = MgoOrganizationDataServiceType.DOCUMENTS,
                ),
                MgoOrganizationDataService(
                  resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/49",
                  type = MgoOrganizationDataServiceType.GP,
                ),
                MgoOrganizationDataService(
                  resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/48",
                  type = MgoOrganizationDataServiceType.BGZ,
                ),
              ),
          )
        assertEquals(expectedFirstHealthProvider, providers.firstOrNull())
      }
    }

  @Test
  fun testSearchDemoSuccess() =
    runTest {
      // Given: successful response
      testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/search.json"))

      // When: Calling searchDemo
      val repository = getRepository()
      val searchFlow = repository.searchDemo()

      // Then: organizations are emitted in the flow
      searchFlow.test {
        val providers = awaitItem()
        assertEquals(45, providers.size)

        val expectedFirstHealthProvider =
          MgoOrganization(
            id = "agb-z:12001468",
            name = "Tandartspraktijk Van Dijck",
            address = "Ginnekenweg 183\r\n4835NA BREDA",
            category = "Tandartsen",
            added = false,
            dataServices =
              listOf(
                MgoOrganizationDataService(
                  resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/51",
                  type = MgoOrganizationDataServiceType.DOCUMENTS,
                ),
                MgoOrganizationDataService(
                  resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/49",
                  type = MgoOrganizationDataServiceType.GP,
                ),
                MgoOrganizationDataService(
                  resourceEndpoint = "https://dva-mock.test.mgo.prolocation.net/48",
                  type = MgoOrganizationDataServiceType.BGZ,
                ),
              ),
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
      fileStore.saveFile(storedMgoOrganizations, name = "organizations.json", clazz = MgoOrganizations::class)

      // When
      val repository = getRepository()

      // Then
      repository.storedOrganizationsFlow.test {
        assertEquals(storedMgoOrganizations.providers, awaitItem())
      }
    }

  @Test
  fun testSave() =
    runTest {
      // When: Calling save
      val provider = TEST_MGO_ORGANIZATION
      val repository = getRepository()
      repository.save(provider)

      // Then: Organization is emitted
      repository.storedOrganizationsFlow.test {
        assertEquals(listOf(provider), awaitItem())
      }
    }

  @Test
  fun testSaveAlreadySaved() =
    runTest {
      // Given: Organization is saved
      val organization = TEST_MGO_ORGANIZATION
      val storedOrganizations = MgoOrganizations(providers = listOf(organization))
      fileStore.saveFile(storedOrganizations, name = "organizations.json", clazz = MgoOrganizations::class)

      // When: Calling save for same organization
      val repository = getRepository()
      repository.save(organization)

      // Then: Organization is emitted, and does not include it twice
      repository.storedOrganizationsFlow.test {
        assertEquals(listOf(organization), awaitItem())
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
      fileStore.saveFile(storedMgoOrganizations, name = "organizations.json", clazz = MgoOrganizations::class)

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

  @Test
  fun `Given stored organizations, When calling deleteAll, Then delete all health care provider from storage`() =
    runTest {
      // Given: saved mgo organizations
      val storedMgoOrganizations =
        MgoOrganizations(
          providers =
            listOf(
              TEST_MGO_ORGANIZATION.copy(id = "1"),
              TEST_MGO_ORGANIZATION.copy(id = "2"),
              TEST_MGO_ORGANIZATION.copy(id = "3"),
            ),
        )
      val repository = getRepository()
      storedMgoOrganizations.providers.forEach { provider -> repository.save(provider) }

      // When: deleting all organizations
      repository.deleteAll()

      // Then: Organizations file is removed
      assertNull(fileStore.getFile(MgoOrganizations::class, "organizations.json"))

      // Then: Flow is empty
      assertEquals(listOf<MgoOrganization>(), repository.get())
    }

  private fun getRepository(): DefaultOrganizationRepository {
    val okHttpClient = TEST_OKHTTP_CLIENT
    val loadApi = createLoadApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
    return DefaultOrganizationRepository(loadApi = loadApi, encryptedFileStore = fileStore)
  }
}
