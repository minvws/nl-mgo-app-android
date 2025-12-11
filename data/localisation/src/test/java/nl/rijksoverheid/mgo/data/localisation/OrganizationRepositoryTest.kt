package nl.rijksoverheid.mgo.data.localisation

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import nl.nl.rijksoverheid.mgo.framework.network.HttpException
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.MgoOrganizations
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OrganizationRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val json = Json.Default
  private val testServer = testServerRule.testServer
  private val mgoStorage = MemoryMgoByteArrayStorage()
  private val okHttpClient = OkHttpClient()
  private lateinit var repository: OrganizationRepository

  @Before
  fun setup() {
    repository = OrganizationRepository(okHttpClient = okHttpClient, baseUrl = testServer.url(), mgoByteArrayStorage = mgoStorage)
  }

  @Test
  fun testSearchSuccess() =
    runTest {
      // Given
      testServer.enqueueJson(json = readResourceFile("load_search_response.json"))

      // When
      val searchFlow = repository.search(name = "name", city = "city", supportedDataServiceIds = listOf("48"))

      // Then
      searchFlow.test {
        val providers = awaitItem()
        assertEquals(45, providers.size)
      }
    }

  @Test
  fun testSearchError() =
    runTest {
      // Given
      testServer.enqueue500()

      // When
      val searchFlow = repository.search(name = "name", city = "city", supportedDataServiceIds = listOf("48"))

      // Then
      searchFlow.test {
        val exception = awaitError() as HttpException
        assertEquals(500, exception.code)
      }
    }

  @Test
  fun testSearchDemoSuccess() =
    runTest {
      // Given
      testServer.enqueueJson(json = readResourceFile("load_search_response.json"))

      // When
      val searchFlow = repository.searchDemo(supportedDataServiceIds = listOf("48"))

      // Then
      searchFlow.test {
        val providers = awaitItem()
        assertEquals(45, providers.size)
      }
    }

  @Test
  fun testSearchDemoError() =
    runTest {
      // Given
      testServer.enqueue500()

      // When
      val searchFlow = repository.searchDemo(supportedDataServiceIds = listOf("48"))

      // Then
      searchFlow.test {
        val exception = awaitError() as HttpException
        assertEquals(500, exception.code)
      }
    }

  @Test
  fun testGetEmpty() =
    runTest {
      // Given no providers

      // When
      repository.get()

      // Then
      repository.storedOrganizationsFlow.test {
        assertEquals(listOf<MgoOrganization>(), awaitItem())
      }
    }

  @Test
  fun testGet() =
    runTest {
      // Given
      val organizations =
        MgoOrganizations(
          providers =
            listOf(
              TEST_MGO_ORGANIZATION
                .copy(id = "1"),
              TEST_MGO_ORGANIZATION
                .copy(id = "2"),
              TEST_MGO_ORGANIZATION
                .copy(id = "3"),
            ),
        )
      val organizationsJson = json.encodeToString<MgoOrganizations>(organizations)
      mgoStorage.save(name = "organizations.json", content = organizationsJson.toByteArray())

      // When
      val storedOrganizations = repository.get()

      // Then
      assertEquals(3, storedOrganizations.size)
    }

  @Test
  fun testSave() =
    runTest {
      // When: Calling save
      val provider = TEST_MGO_ORGANIZATION
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
      val organizations =
        MgoOrganizations(
          providers =
            listOf(
              organization,
            ),
        )
      val organizationsJson = json.encodeToString<MgoOrganizations>(organizations)
      mgoStorage.save(name = "organizations.json", content = organizationsJson.toByteArray())

      // When: Calling save for same organization
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
      // Given stored organizations
      val organizations =
        MgoOrganizations(
          providers =
            listOf(
              TEST_MGO_ORGANIZATION
                .copy(id = "1"),
              TEST_MGO_ORGANIZATION
                .copy(id = "2"),
              TEST_MGO_ORGANIZATION
                .copy(id = "3"),
            ),
        )
      val organizationsJson = json.encodeToString<MgoOrganizations>(organizations)
      mgoStorage.save(name = "organizations.json", content = organizationsJson.toByteArray())

      // When: Deleting organization
      repository.delete(organizations.providers.first().id)

      // Then: Organization is deleted
      repository.storedOrganizationsFlow.test {
        val expectedProviders = organizations.providers.drop(1)
        val storedProviders = awaitItem()
        assertEquals(expectedProviders, storedProviders)
      }
    }

  @Test
  fun `Given stored organizations, When calling deleteAll, Then delete all health care provider from storage`() =
    runTest {
      // Given: saved mgo organizations
      val organizations =
        MgoOrganizations(
          providers =
            listOf(
              TEST_MGO_ORGANIZATION
                .copy(id = "1"),
              TEST_MGO_ORGANIZATION
                .copy(id = "2"),
              TEST_MGO_ORGANIZATION
                .copy(id = "3"),
            ),
        )
      organizations.providers.forEach { provider -> repository.save(provider) }

      // When: deleting all organizations
      repository.deleteAll()

      // Then: Organizations file is removed
      val expected = mgoStorage.get("organizations.json")?.toString(Charsets.UTF_8)
      assertNull(expected)

      // Then: Flow is empty
      assertEquals(listOf<MgoOrganization>(), repository.get())
    }
}
