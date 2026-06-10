package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import app.cash.turbine.test
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_LIFESTYLE
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfHealthCategory
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type.GetHealthCategoryScreenType
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HealthCategoryScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val organizationRepository = mockk<OrganizationRepository>(relaxed = true)
  private val getErrorBanner = mockk<GetErrorBanner>(relaxed = true)
  private val createPdfHealthCategory = mockk<CreatePdfHealthCategory>(relaxed = true)
  private val observeListItemsState = mockk<ObserveListItemsState>(relaxed = true)
  private val retryFailedRequests = mockk<RetryFailedRequests>(relaxed = true)
  private val onClearScreen = mockk<OnClearScreen>(relaxed = true)

  @Before
  fun setup() {
    // Given: Saved organization
    every { organizationRepository.getSaved(any()) } answers { flowOf(listOf(TEST_MGO_ORGANIZATION)) }
  }

  @Test
  fun testInit() =
    runTest {
      // Given: List items
      val state = HealthCategoryScreenViewState.ListItemsState.Loaded(listItemsGroup = listOf())
      every { observeListItemsState.invoke(any(), any(), any()) } answers { flowOf(state) }

      // Given: Error banner
      val banner = null
      every { getErrorBanner.invoke(any(), any()) } answers { flowOf(banner) }

      // When: Creating view model
      val viewModel = createViewModel(category = TEST_HEALTH_CATEGORY_LIFESTYLE, filterOrganization = null)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(state, viewState.listItemsState)
        assertNull(viewState.banner)
      }
    }

  @Test
  fun testRetry() =
    runTest {
      // Given: Viewmodel
      val viewModel = createViewModel(category = TEST_HEALTH_CATEGORY_LIFESTYLE, filterOrganization = null)

      // When: Calling retry
      viewModel.retry()

      // Then: RetryFailedRequests is called
      coVerify(exactly = 1) { retryFailedRequests.invoke(category = TEST_HEALTH_CATEGORY_LIFESTYLE, organizations = listOf(TEST_MGO_ORGANIZATION)) }
    }

  @Test
  fun testGeneratePdf() =
    runTest {
      // Given: Viewmodel
      val viewModel = createViewModel(category = TEST_HEALTH_CATEGORY_LIFESTYLE, filterOrganization = null)

      viewModel.openPdfViewer.test {
        // When: Calling generatePdf
        viewModel.generatePdf()

        // Then: Loading state is emitted
        assertTrue(awaitItem() is PdfViewerState.Loading)

        // Then: Loaded state is emitted
        assertTrue(awaitItem() is PdfViewerState.Loaded)
        expectNoEvents()
      }
    }

  @Test
  fun testGetOrganizationsWithFilter() =
    runTest {
      // Given: Viewmodel with filter organization
      val organization = TEST_MGO_ORGANIZATION.copy(id = "2")
      val viewModel = createViewModel(category = TEST_HEALTH_CATEGORY_LIFESTYLE, filterOrganization = organization)

      // When: Calling getOrganizations
      val organizations = viewModel.getOrganizations()

      // Then: Organization is returned
      assertEquals(listOf(organization), organizations)
    }

  @Test
  fun testGetOrganizationsWithoutFilter() =
    runTest {
      // Given: Viewmodel with no filter organization
      val viewModel = createViewModel(category = TEST_HEALTH_CATEGORY_LIFESTYLE, filterOrganization = null)

      // When: Calling getOrganizations
      val organizations = viewModel.getOrganizations()

      // Then: Organization is returned from saved organizations
      assertEquals(listOf(TEST_MGO_ORGANIZATION), organizations)
    }

  @Test
  fun testClear() {
    // Given: Viewmodel
    val viewModel = createViewModel(category = TEST_HEALTH_CATEGORY_LIFESTYLE, filterOrganization = null)

    // When: Calling clear
    viewModel.clear()

    // Then: OnClearScreen is called
    verify(exactly = 1) { onClearScreen.invoke() }
  }

  fun createViewModel(
    category: HealthCategoryGroup.HealthCategory,
    filterOrganization: MgoOrganization?,
  ) = HealthCategoryScreenViewModel(
    category = category,
    filterOrganization = filterOrganization,
    organizationRepository = organizationRepository,
    getErrorBanner = getErrorBanner,
    createPdfHealthCategory = createPdfHealthCategory,
    observeListItemsState = observeListItemsState,
    retryFailedRequests = retryFailedRequests,
    onClearScreen = onClearScreen,
    getHealthCategoryScreenType = GetHealthCategoryScreenType(),
    ioDispatcher = mainDispatcherRule.testDispatcher,
  )
}
