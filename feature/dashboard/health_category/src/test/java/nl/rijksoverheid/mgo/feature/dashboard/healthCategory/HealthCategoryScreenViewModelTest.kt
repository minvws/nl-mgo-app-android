package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.fhir.DefaultFhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.hcimParser.JvmQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_LIFESTYLE
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HealthCategoryScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val testServerRule = TestServerRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val mgoStorage = MemoryMgoByteArrayStorage()
  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = mgoStorage)
  private val createPdfForHealthCategories = TestCreatePdfForHealthCategories()
  private val okHttpClient = OkHttpClient.Builder().build()
  private val fhirRepository = DefaultFhirRepository(context = context, okHttpClient = okHttpClient, mgoByteArrayStorage = mgoStorage)
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk)
  private val quickJsRepository = JvmQuickJsRepository(dispatcher = mainDispatcherRule.testDispatcher)
  private val jsEngineRepository = JsEngineRepository(quickJsRepository)
  private val mgoResourceParser = MgoResourceParser(jsEngineRepository)
  private val uiSchemaParser = UiSchemaParser(jsEngineRepository)
  private val listItemGroupMapper =
    ListItemGroupMapper(
      context = context,
      mgoResourceParser = mgoResourceParser,
      uiSchemaParser = uiSchemaParser,
      organizationRepository = organizationRepository,
      getDataSetsFromDisk = getDataSetsFromDisk,
      mgoByteArrayStorage = mgoStorage,
    )
  private val mgoResourceStore = MgoResourceStore()

  @Before
  fun setup() =
    runTest {
      quickJsRepository.create()
      organizationRepository.deleteAll()
    }

  private suspend fun enqueueEmptyBundles() {
    fetchFhirResponseSuccess(
      responseJson = readResourceFile("emptyBundle.json"),
      endpointId = "alcoholUse",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("emptyBundle.json"),
      endpointId = "drugUse",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("emptyBundle.json"),
      endpointId = "livingSituation",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("emptyBundle.json"),
      endpointId = "nutritionAdvice",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("emptyBundle.json"),
      endpointId = "tobaccoUse",
    )
  }

  private suspend fun enqueueLifestyleResponses() {
    fetchFhirResponseSuccess(
      responseJson = readResourceFile("alcoholUse.json"),
      endpointId = "alcoholUse",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("drugUse.json"),
      endpointId = "drugUse",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("livingSituation.json"),
      endpointId = "livingSituation",
    )

    fetchFhirResponseSuccess(
      responseJson = readResourceFile("nutritionAdvice.json"),
      endpointId = "nutritionAdvice",
    )

    fetchFhirResponseFailed(
      endpointId = "tobaccoUse",
    )
  }

  private suspend fun fetchFhirResponseSuccess(
    responseJson: String,
    endpointId: String,
  ) {
    testServerRule.testServer.enqueueJson(responseJson)
    val request =
      FhirRequest(
        organizationId = TEST_MGO_ORGANIZATION.id,
        medmijId = "1",
        dataServiceId = "48",
        endpointId = endpointId,
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServerRule.testServer.url().toStr(),
        endpointPath = "",
      )
    fhirRepository.fetch(
      request = request,
      forceRefresh = true,
    )
  }

  private suspend fun fetchFhirResponseFailed(endpointId: String) {
    testServerRule.testServer.enqueue500()
    val request =
      FhirRequest(
        organizationId = TEST_MGO_ORGANIZATION.id,
        medmijId = "1",
        dataServiceId = "48",
        endpointId = endpointId,
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServerRule.testServer.url().toStr(),
        endpointPath = "",
      )
    fhirRepository.fetch(
      request = request,
      forceRefresh = true,
    )
  }

  @Test
  fun testEmpty() =
    runTest {
      // Given: Stored organization that does not have any data for the lifestyle category
      val organization =
        TEST_MGO_ORGANIZATION.copy(
          dataServices =
            listOf(
              TEST_GP_DATA_SERVICE,
            ),
        )
      organizationRepository.save(organization)

      // Given: Lifestyle responses
      enqueueLifestyleResponses()

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertTrue(viewState.listItemsState is HealthCategoryScreenViewState.ListItemsState.NoData)
      }
    }

  @Test
  fun testLoaded() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Lifestyle responses
      enqueueLifestyleResponses()

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertTrue(viewState.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded)
        assertTrue(viewState.showErrorBanner)
        val loaded = viewState.listItemsState as HealthCategoryScreenViewState.ListItemsState.Loaded
        assertEquals(5, loaded.listItemsGroup.size)
      }
    }

  @Test
  fun testLoadedAllEmpty() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Lifestyle responses
      enqueueEmptyBundles()

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertTrue(viewState.listItemsState is HealthCategoryScreenViewState.ListItemsState.NoData)
      }
    }

  @Test
  fun testLoadedFilterOrganization() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Lifestyle responses
      enqueueLifestyleResponses()

      // When: Creating viewmodel
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertTrue(viewState.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded)
        assertTrue(viewState.showErrorBanner)
        val loaded = viewState.listItemsState as HealthCategoryScreenViewState.ListItemsState.Loaded
        assertEquals(5, loaded.listItemsGroup.size)
      }
    }

  @Test
  fun testRetry() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Lifestyle responses
      enqueueLifestyleResponses()

      // When: Creating viewmodel
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Given: All responses for upcoming retry are successful
      testServerRule.testServer.enqueueJson(readResourceFile("livingSituation.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("drugUse.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("alcoholUse.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("tobaccoUse.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("nutritionAdvice.json"))

      // When: Calling retry
      viewModel.retry()

      // Then: Error banner is no longer showing
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertFalse(viewState.showErrorBanner)
      }
    }

  @Test
  fun testGeneratePdf() =
    runTest {
      // Given: viewmodel
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

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

  @Test(expected = IllegalStateException::class)
  fun testOnCleared() =
    runTest {
      // Given: viewmodel
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Given: Mgo resource is stored in store
      mgoResourceStore.store(TEST_MGO_RESOURCE)

      // When: Calling onCleared
      viewModel.clear()

      // Then: Store is cleared
      mgoResourceStore.get("1")
    }

  private fun createViewModel(
    filterOrganization: MgoOrganization? = null,
    category: HealthCategoryGroup.HealthCategory,
  ) = HealthCategoryScreenViewModel(
    category = category,
    filterOrganization = filterOrganization,
    ioDispatcher = mainDispatcherRule.testDispatcher,
    dvaApiBaseUrl = testServerRule.testServer.url().toStr(),
    organizationRepository = organizationRepository,
    createPdf = createPdfForHealthCategories,
    fhirRepository = fhirRepository,
    getEndpointsForHealthCategory = getEndpointsForHealthCategory,
    listItemGroupMapper = listItemGroupMapper,
    mgoResourceStore = mgoResourceStore,
  )
}
