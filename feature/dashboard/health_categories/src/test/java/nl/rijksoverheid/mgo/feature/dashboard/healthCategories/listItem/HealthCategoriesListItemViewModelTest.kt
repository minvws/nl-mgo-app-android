package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SUCCESS
import nl.rijksoverheid.mgo.data.fhir.TestFhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HealthCategoriesListItemViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk)
  private val fhirRepository = TestFhirRepository()

  @Test
  fun testLoaded() =
    runTest {
      // Given: stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Fhir repository returns success that is non empty
      fhirRepository.setObserveResult(TEST_FHIR_RESPONSE_SUCCESS(isEmpty = false))

      // When: Creating viewmodel and not filtering on organization
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_PROBLEMS)

      // Then: List item state is loaded
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADED)
      }
    }

  @Test
  fun testLoadedFilterOrganization() =
    runTest {
      // Given: stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Fhir repository returns success that is non empty
      fhirRepository.setObserveResult(TEST_FHIR_RESPONSE_SUCCESS(isEmpty = false))

      // When: Creating viewmodel and filtering on organization
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_PROBLEMS)

      // Then: List item state is loaded
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADED)
      }
    }

  @Test
  fun testNoData() =
    runTest {
      // Given: stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Fhir repository returns success that is non empty
      fhirRepository.setObserveResult(TEST_FHIR_RESPONSE_SUCCESS(isEmpty = true))

      // When: Creating viewmodel and filtering on organization
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_PROBLEMS)

      // Then: List item state is loaded
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
      }
    }

  @Test
  fun testMissingDataService() =
    runTest {
      // Given: stored organization
      val organization =
        TEST_MGO_ORGANIZATION.copy(
          dataServices = listOf(nl.rijksoverheid.mgo.component.organization.TEST_DOCUMENTS_DATA_SERVICE),
        )
      organizationRepository.save(organization)

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = organization, category = TEST_HEALTH_CATEGORY_PROBLEMS)

      // Then: List item state is loaded
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
      }
    }

  private fun createViewModel(
    filterOrganization: MgoOrganization?,
    category: HealthCategoryGroup.HealthCategory,
  ) = HealthCategoriesListItemViewModel(
    filterOrganization = filterOrganization,
    category = category,
    getEndpointsForHealthCategory = getEndpointsForHealthCategory,
    organizationRepository = organizationRepository,
    fhirRepository = fhirRepository,
    ioDispatcher = mainDispatcherRule.testDispatcher,
  )
}
