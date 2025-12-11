package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import app.cash.turbine.test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OrganizationListAutomaticScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule =
    MainDispatcherRule()

  @get:Rule
  val testServerRule = TestServerRule()

  private val okHttpClient = OkHttpClient()
  private val mgoByteArrayStorage = MemoryMgoByteArrayStorage()
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private lateinit var organizationRepository: OrganizationRepository

  @Before
  fun setup() {
    organizationRepository =
      OrganizationRepository(okHttpClient = okHttpClient, baseUrl = testServerRule.testServer.url(), mgoByteArrayStorage = mgoByteArrayStorage)
  }

  private fun createViewModel() =
    OrganizationListAutomaticScreenViewModel(
      ioDispatcher = mainDispatcherRule.testDispatcher,
      organizationRepository = organizationRepository,
      getDataSetsFromDisk = getDataSetsFromDisk,
    )

  @Test
  fun testSuccess() =
    runTest {
      // Given
      testServerRule.testServer.enqueueJson(json = readResourceFile("load_search_response.json"))
      val viewModel = createViewModel()

      // When
      viewModel.viewState.test {
        val viewState = awaitItem()

        // Then
        assertFalse(viewState.loading)
        assertTrue(viewState.error == null)
        assertTrue(viewState.results.isNotEmpty())
      }
    }

  @Test
  fun testError() =
    runTest {
      // Given
      testServerRule.testServer.enqueue500()
      val viewModel = createViewModel()

      // When
      viewModel.viewState.test {
        val viewState = awaitItem()

        // Then
        assertFalse(viewState.loading)
        assertTrue(viewState.error != null)
        assertTrue(viewState.results.isEmpty())
      }
    }

  @Test
  fun testUpdateOrganization() =
    runTest {
      // Given
      testServerRule.testServer.enqueueJson(json = readResourceFile("load_search_response.json"))
      val viewModel = createViewModel()

      viewModel.viewState.test {
        assertFalse(awaitItem().results.first().added)

        // When
        val firstOrganization =
          viewModel.viewState.value.results
            .first()
        viewModel.updateOrganization(organization = firstOrganization, added = true)

        // Then
        assertTrue(awaitItem().results.first().added)
      }
    }

  @Test
  fun testUpdateOrganizations() =
    runTest {
      // Given: Organizations
      testServerRule.testServer.enqueueJson(json = readResourceFile("load_search_response.json"))
      val viewModel = createViewModel()
      val viewState = runBlocking { viewModel.viewState.first() }

      val firstOrganization = viewState.results[0]
      val secondOrganization = viewState.results[1]

      // Given: First organization stored
      organizationRepository.save(firstOrganization)

      // Given: First organization removed (in the UI)
      viewModel.updateOrganization(organization = firstOrganization, added = false)

      // Given: Second organization added (in the UI)
      viewModel.updateOrganization(organization = secondOrganization, added = true)

      // When: Calling updateOrganizations
      viewModel.updateOrganizations()

      // Then: First organization is not added
      val storedOrganizations = organizationRepository.get()
      assertFalse(storedOrganizations[0].added)

      // Then: Second organization is added
      assertTrue(storedOrganizations[1].added)
    }
}
