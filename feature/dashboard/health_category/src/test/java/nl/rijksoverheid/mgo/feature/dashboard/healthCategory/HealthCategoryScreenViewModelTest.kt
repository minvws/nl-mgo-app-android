package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.error.DefaultGetErrorBanner
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.data.fhir.FhirRepositoryRule
import nl.rijksoverheid.mgo.data.fhir.FhirResponseJson
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_ALCOHOL_USE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_DRUG_USE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_LIVING_SITUATION
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_NUTRITION_ADVICE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_TOBACCO_USE
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
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
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

  private val byteArrayStorage = MemoryMgoByteArrayStorage()

  @get:Rule
  val fhirRepositoryRule = FhirRepositoryRule(byteArrayStorage)

  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = byteArrayStorage)
  private val createPdfForHealthCategories = TestCreatePdfForHealthCategories()
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
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
      mgoByteArrayStorage = byteArrayStorage,
    )
  private val mgoResourceStore = MgoResourceStore()
  private val getRequests = GetRequests(getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk))
  private val listItemStateMapper = ListItemStateMapper(listItemGroupMapper = listItemGroupMapper, mgoResourceStore = mgoResourceStore)
  private lateinit var observeFhirResponses: ObserveFhirResponses
  private lateinit var getErrorBanner: GetErrorBanner

  @Before
  fun setup() =
    runTest {
      quickJsRepository.create()
      organizationRepository.deleteAll()
      observeFhirResponses = ObserveFhirResponses(getRequests = getRequests, fhirRepository = fhirRepositoryRule.getRepository())
      getErrorBanner = DefaultGetErrorBanner(getRequests = getRequests, observeFhirResponses = observeFhirResponses)
    }

  @Test
  fun testInit() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All lifestyle responses are success
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.DRUG_USE, request = TEST_FHIR_REQUEST_DRUG_USE)
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.ALCOHOL_USE, request = TEST_FHIR_REQUEST_ALCOHOL_USE)
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.LIVING_SITUATION,
        request = TEST_FHIR_REQUEST_LIVING_SITUATION,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.NUTRITION_ADVICE,
        request = TEST_FHIR_REQUEST_NUTRITION_ADVICE,
      )
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.TOBACCO_USE, request = TEST_FHIR_REQUEST_TOBACCO_USE)

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
      fhirRepositoryRule.enqueueErrorResponse(request = TEST_FHIR_REQUEST_DRUG_USE)
      fhirRepositoryRule.enqueueErrorResponse(request = TEST_FHIR_REQUEST_ALCOHOL_USE)
      fhirRepositoryRule.enqueueErrorResponse(request = TEST_FHIR_REQUEST_LIVING_SITUATION)
      fhirRepositoryRule.enqueueErrorResponse(request = TEST_FHIR_REQUEST_NUTRITION_ADVICE)
      fhirRepositoryRule.enqueueErrorResponse(request = TEST_FHIR_REQUEST_TOBACCO_USE)

      // When: Creating viewmodel
      val viewModel =
        createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Given: All lifestyle responses are success
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.DRUG_USE,
        request = TEST_FHIR_REQUEST_DRUG_USE,
        fetch = false,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.ALCOHOL_USE,
        request = TEST_FHIR_REQUEST_ALCOHOL_USE,
        fetch = false,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.LIVING_SITUATION,
        request = TEST_FHIR_REQUEST_LIVING_SITUATION,
        fetch = false,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.NUTRITION_ADVICE,
        request = TEST_FHIR_REQUEST_NUTRITION_ADVICE,
        fetch = false,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.TOBACCO_USE,
        request = TEST_FHIR_REQUEST_TOBACCO_USE,
        fetch = false,
      )

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
    organizationRepository = organizationRepository,
    createPdf = createPdfForHealthCategories,
    fhirRepository = fhirRepositoryRule.getRepository(),
    mgoResourceStore = mgoResourceStore,
    getErrorBanner = getErrorBanner,
    observeFhirResponses = observeFhirResponses,
    listItemStateMapper = listItemStateMapper,
    getRequests = getRequests,
  )
}
