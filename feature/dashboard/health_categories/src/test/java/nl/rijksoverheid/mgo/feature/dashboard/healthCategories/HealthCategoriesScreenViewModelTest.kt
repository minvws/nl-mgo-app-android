package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.DefaultFhirRepository
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner.TestDefaultGetHealthCategoriesBanner
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class HealthCategoriesScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val testServerRule = TestServerRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()

  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val favoriteRepository = FavoriteHealthCategoriesRepository(keyValueStorage)

  private val okHttpClient = OkHttpClient()
  private val organizationRepository = OrganizationRepository(okHttpClient = okHttpClient, baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val keyValueStore = TestKeyValueStore()
  private val mgoByteArrayStorage = MemoryMgoByteArrayStorage()
  private val fhirRepository = DefaultFhirRepository(context = context, okHttpClient = okHttpClient, mgoByteArrayStorage = mgoByteArrayStorage)

  @Before
  fun setup() =
    runTest {
      organizationRepository.save(TEST_MGO_ORGANIZATION)
    }

  @Test
  fun testCreateViewModel() =
    runTest {
      // Given: First category is marked as favorite
      val firstCategory = getHealthCategoriesFromDisk()[0].categories[0].id
      favoriteRepository.store(listOf(firstCategory))

      // Given: Viewmodel
      val viewModel = createViewModel()

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.favorites.size)
        assertEquals(4, viewState.groups.size)
      }
    }

  @Test
  fun testRetryFailedOnly() =
    runTest {
      // Given: Fhir response failed
      testServerRule.testServer.enqueue500()
      fhirRepository.fetch(TEST_FHIR_REQUEST.copy(url = testServerRule.testServer.url()), true)

      // Given Next fhir response is success
      val alcoholUseJson = readResourceFile("alcoholUse.json")
      testServerRule.testServer.enqueueJson(alcoholUseJson)

      // When: Calling retry
      val viewModel = createViewModel()
      viewModel.retry(failedOnly = true)

      // Then: Observing fhir response returns success
      fhirRepository.observe().test {
        val fhirResponses = awaitItem()
        assertEquals(1, fhirResponses.size)
      }
    }

  @Test
  fun testRetryAll() =
    runTest {
      // Given: Fhir response success
      val alcoholUseJson = readResourceFile("alcoholUse.json")
      testServerRule.testServer.enqueueJson(alcoholUseJson)
      fhirRepository.fetch(TEST_FHIR_REQUEST.copy(url = testServerRule.testServer.url()), true)

      // Given Next fhir response is success
      testServerRule.testServer.enqueueJson(alcoholUseJson)

      // When: Calling retry
      val viewModel = createViewModel()
      viewModel.retry(failedOnly = false)

      // Then: Observing fhir response returns success
      fhirRepository.observe().test {
        val fhirResponses = awaitItem()
        assertEquals(1, fhirResponses.size)
      }
    }

  private fun createViewModel() =
    HealthCategoriesScreenViewModel(
      favoriteRepository = favoriteRepository,
      organizationRepository = organizationRepository,
      getHealthCategoriesFromDisk = getHealthCategoriesFromDisk,
      keyValueStore = keyValueStore,
      ioDispatcher = mainDispatcherRule.testDispatcher,
      getHealthCategoriesBanner = TestDefaultGetHealthCategoriesBanner(),
      fhirRepository = fhirRepository,
      dvaApiBaseUrl = testServerRule.testServer.url(),
    )
}
