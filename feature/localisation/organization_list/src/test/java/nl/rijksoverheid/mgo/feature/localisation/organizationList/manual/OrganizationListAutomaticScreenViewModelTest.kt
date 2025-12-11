package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
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
    OrganizationListManualScreenViewModel(
      name = "Tandarts",
      city = "Roermond",
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
  fun `Given viewmodel, When saving health care provider, Then navigate`() =
    runTest {
      // Given
      val viewModel = createViewModel()

      viewModel.navigation.test {
        // When
        viewModel.addOrganization(TEST_MGO_ORGANIZATION)

        // Then
        assertEquals(Unit, awaitItem())
      }
    }
}
