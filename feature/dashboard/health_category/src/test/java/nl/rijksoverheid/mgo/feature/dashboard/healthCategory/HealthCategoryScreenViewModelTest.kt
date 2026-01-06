package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.error.DefaultGetErrorBanner
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import nl.rijksoverheid.mgo.component.error.TestGetErrorBanner
import nl.rijksoverheid.mgo.component.fhir.GetEndpoints
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
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
import org.junit.Assert.assertNull
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
  private val getEndpoints = GetEndpoints(getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk))
  private val listItemStateMapper = ListItemStateMapper(listItemGroupMapper = listItemGroupMapper, mgoResourceStore = mgoResourceStore)
  private lateinit var fhirRepository: DefaultFhirRepository
  private lateinit var observeFhirResponses: ObserveFhirResponses
  private lateinit var getErrorBanner: GetErrorBanner

  @Before
  fun setup() =
    runTest {
      quickJsRepository.create()
      organizationRepository.deleteAll()
      fhirRepository =
        DefaultFhirRepository(context = context, okHttpClient = okHttpClient, mgoByteArrayStorage = mgoStorage, dvaApiBaseUrl = testServerRule.testServer.url())
      observeFhirResponses = ObserveFhirResponses(getEndpoints = getEndpoints, fhirRepository = fhirRepository)
      getErrorBanner = DefaultGetErrorBanner(getEndpoints = getEndpoints, observeFhirResponses = observeFhirResponses)
    }

  @Test
  fun testInit() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All lifestyle responses are success
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

      fetchFhirResponseSuccess(
        responseJson = readResourceFile("tobaccoUse.json"),
        endpointId = "tobaccoUse",
      )

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()

        // Then: List item state is updated
        assertTrue(viewState.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded)
        val loaded = viewState.listItemsState as HealthCategoryScreenViewState.ListItemsState.Loaded
        assertEquals(5, loaded.listItemsGroup.size)

        // Then: Error banner state is updated
        assertNull(viewState.banner)
      }
    }

  @Test
  fun testRetry() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All lifestyle responses fail
      fetchFhirResponseFailed(
        endpointId = "alcoholUse",
      )

      fetchFhirResponseFailed(
        endpointId = "drugUse",
      )

      fetchFhirResponseFailed(
        endpointId = "livingSituation",
      )

      fetchFhirResponseFailed(
        endpointId = "nutritionAdvice",
      )

      fetchFhirResponseFailed(
        endpointId = "tobaccoUse",
      )

      // When: Creating viewmodel
      val viewModel =
        createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Given: All lifestyle responses are success
      testServerRule.testServer.enqueueJson(readResourceFile("livingSituation.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("drugUse.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("alcoholUse.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("tobaccoUse.json"))
      testServerRule.testServer.enqueueJson(readResourceFile("nutritionAdvice.json"))

      // When: Calling retry
      viewModel.retry()

      // Then: Error banner is not showing
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertNull(viewState.banner)
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
    mgoResourceStore = mgoResourceStore,
    getErrorBanner = getErrorBanner,
    observeFhirResponses = observeFhirResponses,
    listItemStateMapper = listItemStateMapper,
  )

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
        endpointPath = "",
      )
    fhirRepository.fetch(
      request = request,
      forceRefresh = true,
    )
  }
}
