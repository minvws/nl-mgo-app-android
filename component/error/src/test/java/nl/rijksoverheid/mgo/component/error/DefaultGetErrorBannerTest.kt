package nl.rijksoverheid.mgo.component.error

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.FhirRepositoryRule
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
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
  val fhirRepositoryRule = FhirRepositoryRule(MemoryMgoByteArrayStorage())

  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk)
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val getRequests: GetRequests = GetRequests(getEndpointsForHealthCategory = getEndpointsForHealthCategory)

  private lateinit var getHealthCategoriesBanner: DefaultGetErrorBanner
  private lateinit var observeFhirResponses: ObserveFhirResponses

  @Before
  fun setup() {
    observeFhirResponses =
      ObserveFhirResponses(
        getRequests = getRequests,
        fhirRepository = fhirRepositoryRule.getRepository(),
      )

    getHealthCategoriesBanner =
      DefaultGetErrorBanner(
        getRequests = getRequests,
        observeFhirResponses = observeFhirResponses,
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
      val requests = getRequests(organizations = organizations, categories = categories)
      for (request in requests) {
        fhirRepositoryRule.enqueueIoException()
        fhirRepositoryRule.getRepository().fetch(request = request, forceRefresh = true)
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
      val requests = getRequests(organizations = organizations, categories = categories)
      for (request in requests) {
        fhirRepositoryRule.enqueueIoException()
        fhirRepositoryRule.getRepository().fetch(request = request, forceRefresh = true)
      }
      fhirRepositoryRule.enqueueEmptyJson()
      fhirRepositoryRule.getRepository().fetch(request = requests.first(), forceRefresh = true)

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
      val requests = getRequests(organizations = organizations, categories = categories)
      for (request in requests) {
        fhirRepositoryRule.enqueueErrorResponse(request = request, fetch = false)
        fhirRepositoryRule.getRepository().fetch(request = request, forceRefresh = true)
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
      val requests = getRequests(organizations = organizations, categories = categories)
      for (request in requests) {
        fhirRepositoryRule.enqueueErrorResponse(request = request, fetch = false)
        fhirRepositoryRule.getRepository().fetch(request = request, forceRefresh = true)
      }
      fhirRepositoryRule.enqueueEmptyJson()
      fhirRepositoryRule.getRepository().fetch(request = requests.first(), forceRefresh = true)

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
      val requests = getRequests(organizations = organizations, categories = categories)
      for (request in requests) {
        fhirRepositoryRule.enqueueEmptyJson()
        fhirRepositoryRule.getRepository().fetch(request = request, forceRefresh = true)
      }

      // When: Observing the banner
      getHealthCategoriesBanner.invoke(organizations = organizations, categories = categories).test {
        // Then: Banner is emitted
        assertNull(awaitItem())
      }
    }
}
