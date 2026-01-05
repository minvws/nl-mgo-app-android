package nl.rijksoverheid.mgo.component.error

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.fhir.GetEndpoints
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.DefaultFhirRepository
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultGetErrorBannerTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk)
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val getEndpoints: GetEndpoints = GetEndpoints(getEndpointsForHealthCategory = getEndpointsForHealthCategory)
  private val request = TEST_FHIR_REQUEST.copy(organizationId = TEST_MGO_ORGANIZATION.id, dataServiceId = TEST_MGO_ORGANIZATION.dataServices.first().id)

  private lateinit var fhirRepository: DefaultFhirRepository
  private lateinit var getHealthCategoriesBanner: DefaultGetErrorBanner

  @Before
  fun setup() {
    fhirRepository =
      DefaultFhirRepository(
        context = context,
        okHttpClient = OkHttpClient(),
        mgoByteArrayStorage = MemoryMgoByteArrayStorage(),
        dvaApiBaseUrl = testServerRule.testServer.url(),
      )

    getHealthCategoriesBanner =
      DefaultGetErrorBanner(
        getEndpoints = getEndpoints,
        fhirRepository = fhirRepository,
      )
  }

  @Test
  fun testUserErrorBanner() =
    runTest {
      // Given: Organization
      val organizations = listOf(TEST_MGO_ORGANIZATION)

      // Given: Categories
      val categories = getHealthCategoriesFromDisk().map { groups -> groups.categories }.flatten()

      // Given: All requests fail because of user error
      val dataSet = getDataSetsFromDisk().first { dataSet -> dataSet.id == "48" }
      for (endpoint in dataSet.endpoints) {
        testServerRule.testServer.enqueueIoException()
        val request = request.copy(endpointId = endpoint.id)
        fhirRepository.fetch(request = request, forceRefresh = true)
      }

      // When: Observing the banner
      getHealthCategoriesBanner.invoke(categories = categories, organizations = organizations).test {
        // Then: Banner is emitted
        assertEquals(
          ErrorBannerState.Error.UserError(false),
          awaitItem(),
        )
      }
    }

  @Test
  fun testUserErrorPartialBanner() =
    runTest {
      // Given: Organization
      val organizations = listOf(TEST_MGO_ORGANIZATION)

      // Given: Categories
      val categories = getHealthCategoriesFromDisk().map { groups -> groups.categories }.flatten()

      // Given: All requests except the first one fail because of user error
      val dataSet = getDataSetsFromDisk().first { dataSet -> dataSet.id == "48" }
      for (endpoint in dataSet.endpoints) {
        testServerRule.testServer.enqueueIoException()
        val request = request.copy(endpointId = endpoint.id)
        fhirRepository.fetch(request = request, forceRefresh = true)
      }
      testServerRule.testServer.enqueueJson("{}")
      val request = request.copy(endpointId = dataSet.endpoints.first().id)
      fhirRepository.fetch(request = request, forceRefresh = true)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke(categories = categories, organizations = organizations).test {
        // Then: Banner is emitted
        assertEquals(
          ErrorBannerState.Error.UserError(true),
          awaitItem(),
        )
      }
    }

  @Test
  fun testServerErrorBanner() =
    runTest {
      // Given: Organization
      val organizations = listOf(TEST_MGO_ORGANIZATION)

      // Given: Categories
      val categories = getHealthCategoriesFromDisk().map { groups -> groups.categories }.flatten()

      // Given: All requests fail because of server error
      val dataSet = getDataSetsFromDisk().first { dataSet -> dataSet.id == "48" }
      for (endpoint in dataSet.endpoints) {
        testServerRule.testServer.enqueue500()
        val request = request.copy(endpointId = endpoint.id)
        fhirRepository.fetch(request = request, forceRefresh = true)
      }

      // When: Observing the banner
      getHealthCategoriesBanner.invoke(organizations = organizations, categories = categories).test {
        // Then: Banner is emitted
        assertEquals(
          ErrorBannerState.Error.ServerError(false),
          awaitItem(),
        )
      }
    }

  @Test
  fun testServerErrorPartialBanner() =
    runTest {
      // Given: Organization
      val organizations = listOf(TEST_MGO_ORGANIZATION)

      // Given: Categories
      val categories = getHealthCategoriesFromDisk().map { groups -> groups.categories }.flatten()

      // Given: All requests except the first one fail because of server error
      val dataSet = getDataSetsFromDisk().first { dataSet -> dataSet.id == "48" }
      for (endpoint in dataSet.endpoints) {
        testServerRule.testServer.enqueue500()
        val request = request.copy(endpointId = endpoint.id)
        fhirRepository.fetch(request = request, forceRefresh = true)
      }
      testServerRule.testServer.enqueueJson("{}")
      val request = request.copy(endpointId = dataSet.endpoints.first().id)
      fhirRepository.fetch(request = request, forceRefresh = true)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke(organizations = organizations, categories = categories).test {
        // Then: Banner is emitted
        assertEquals(
          ErrorBannerState.Error.ServerError(true),
          awaitItem(),
        )
      }
    }

  @Test
  fun testLoadedBanner() =
    runTest {
      // Given: Organization
      val organizations = listOf(TEST_MGO_ORGANIZATION)

      // Given: Categories
      val categories = getHealthCategoriesFromDisk().map { groups -> groups.categories }.flatten()

      // Given: All requests success
      val dataSet = getDataSetsFromDisk().first { dataSet -> dataSet.id == "48" }
      for (endpoint in dataSet.endpoints) {
        testServerRule.testServer.enqueueJson("{}")
        val request = request.copy(endpointId = endpoint.id)
        fhirRepository.fetch(request = request, forceRefresh = true)
      }

      // When: Observing the banner
      getHealthCategoriesBanner.invoke(organizations = organizations, categories = categories).test {
        // Then: Banner is emitted
        assertNull(awaitItem())
      }
    }
}
