package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.error.TestGetErrorBanner
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.FhirRepositoryRule
import nl.rijksoverheid.mgo.data.fhir.FhirResponseJson
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_ALCOHOL_USE
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.data.organization.createOrganizationRepositoryForJvm
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesScreenViewModelTest {
  private val mgoByteArrayStorage = MemoryMgoByteArrayStorage()

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val testServerRule = TestServerRule()

  @get:Rule
  val fhirRepositoryRule = FhirRepositoryRule(mgoByteArrayStorage)

  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val favoriteRepository = FavoriteHealthCategoriesRepository(keyValueStorage)

  private lateinit var organizationRepository: OrganizationRepository
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val keyValueStore = TestKeyValueStore()
  private val getRequests = GetRequests(getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk = JvmGetDataSetsFromDisk()))

  @Before
  fun setup() =
    runTest {
      organizationRepository = createOrganizationRepositoryForJvm()
      organizationRepository.addAndSave(TEST_MGO_ORGANIZATION)
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
  fun testRetry() =
    runTest {
      // Given: Fhir response failed
      fhirRepositoryRule.enqueueErrorResponse(request = TEST_FHIR_REQUEST_ALCOHOL_USE)

      // Given Next fhir response is success
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.ALCOHOL_USE,
        request = TEST_FHIR_REQUEST_ALCOHOL_USE,
        fetch = false,
      )

      // When: Calling retry
      val viewModel = createViewModel()
      viewModel.retry()

      // Then: Observing fhir response returns success
      fhirRepositoryRule.getRepository().observe().test {
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
      getErrorBanner = TestGetErrorBanner(),
      fhirRepository = fhirRepositoryRule.getRepository(),
      filterOrganization = null,
      getRequests = getRequests,
    )
}
