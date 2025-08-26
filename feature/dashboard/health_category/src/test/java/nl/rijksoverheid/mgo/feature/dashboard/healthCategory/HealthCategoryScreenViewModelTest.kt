package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_ERROR
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.TestHealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.TestMgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HealthCategoryScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(listOf())
  private val organizationRepository =
    TestOrganizationRepository().apply {
      setStoredProviders(listOf(TEST_MGO_ORGANIZATION))
    }
  private val mgoResourceRepository = TestMgoResourceRepository()
  private val createPdf = TestCreatePdfForHealthCategories()

  @Before
  fun setUp() {
    createPdf.reset()
  }

  @Test
  fun testLoadedState() =
    runTest {
      // Given: Show all mgo resources
      mgoResourceRepository.setMgoResourcesFiltered(listOf(TEST_MGO_RESOURCE))

      // Given: Health care data state has loaded state
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel(TEST_MGO_ORGANIZATION)

      // Then: List items state loaded
      viewModel.viewState.test {
        val emit = awaitItem()
        assertTrue(emit.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded)
      }
    }

  @Test
  fun testRetryForOrganizationAndCategory() =
    runTest {
      // Given: Show all mgo resources
      mgoResourceRepository.setMgoResourcesFiltered(listOf(TEST_MGO_RESOURCE))

      // Given: Health care data state has error state
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_ERROR))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS)

      // Given: Upon next refresh data state is loaded
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))

      // When: Calling retry
      val viewModel = createViewModel(TEST_MGO_ORGANIZATION)
      viewModel.retry()

      viewModel.viewState.test {
        // Then: error banner is shown
        assertFalse(awaitItem().showErrorBanner)
      }
    }

  @Test
  fun testRetryForOrganization() =
    runTest {
      // Given: Show all mgo resources
      mgoResourceRepository.setMgoResourcesFiltered(listOf(TEST_MGO_RESOURCE))

      // Given: Health care data state has error state
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_ERROR))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS)

      // Given: Upon next refresh data state is loaded
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))

      // When: Calling retry
      val viewModel = createViewModel(null)
      viewModel.retry()

      viewModel.viewState.test {
        // Then: error banner is shown
        assertFalse(awaitItem().showErrorBanner)
      }
    }

  @Test
  fun testGeneratePdf() =
    runTest {
      // Given
      val viewModel = createViewModel(TEST_MGO_ORGANIZATION)

      // When
      viewModel.generatePdf()

      // Then
      assertTrue(createPdf.assertPdfGenerated())
    }

  private fun createViewModel(organization: MgoOrganization?): HealthCategoryScreenViewModel =
    HealthCategoryScreenViewModel(
      category = HealthCareCategoryId.MEDICATIONS,
      filterOrganization = organization,
      healthCareDataStatesRepository = healthCareDataStatesRepository,
      organizationRepository = organizationRepository,
      uiSchemaMapper = TestUiSchemaMapper(),
      mgoResourceRepository = mgoResourceRepository,
      createPdf = createPdf,
      ioDispatcher = mainDispatcherRule.testDispatcher,
    )
}
