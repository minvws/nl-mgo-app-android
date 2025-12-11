package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSectionMapper
import nl.rijksoverheid.mgo.data.fhir.FhirBinary
import nl.rijksoverheid.mgo.data.fhir.TestFhirRepository
import nl.rijksoverheid.mgo.data.hcimParser.JvmQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class UISchemaScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val fhirRepository = TestFhirRepository()
  private val mgoResourceStore = MgoResourceStore()
  private val uiSchemaSectionMapper = UISchemaSectionMapper(mgoResourceStore)
  private val quickJsRepository = JvmQuickJsRepository(mainDispatcherRule.testDispatcher)
  private val jsEngineRepository = JsEngineRepository(quickJsRepository)
  private val mgoResourceParser = MgoResourceParser(jsEngineRepository)
  private val uiSchemaParser = UiSchemaParser(jsEngineRepository)

  @Before
  fun setup() =
    runTest {
      quickJsRepository.create()
    }

  @Test
  fun testInitSummary() =
    runTest {
      // Given alcohol use mgo resource is stored
      val referenceId = setAlcoholUseMgoResource()

      // Given: summary viewmodel
      val viewModel =
        createViewModel(
          organization = TEST_MGO_ORGANIZATION,
          referenceId = referenceId,
          isSummary = true,
        )

      // Then: view state is updated
      viewModel.viewState.test {
        assertEquals(1, awaitItem().sections.size)
      }
    }

  @Test
  fun testInitDetails() =
    runTest {
      // Given alcohol use mgo resource is stored
      val referenceId = setAlcoholUseMgoResource()

      // Given: summary viewmodel
      val viewModel =
        createViewModel(
          organization = TEST_MGO_ORGANIZATION,
          referenceId = referenceId,
          isSummary = false,
        )

      // Then: view state is updated
      viewModel.viewState.test {
        assertEquals(1, awaitItem().sections.size)
      }
    }

  @Test
  fun testOnClickReferenceRow() =
    runTest {
      // Given alcohol use mgo resource is stored
      val referenceId = setAlcoholUseMgoResource()

      // Given: viewmodel
      val viewModel =
        createViewModel(
          organization = TEST_MGO_ORGANIZATION,
          referenceId = referenceId,
          isSummary = false,
        )

      viewModel.navigate.test {
        // When: Calling onClickReferenceRow
        viewModel.onClickReferenceRow(row = UISchemaRow.Reference(heading = null, value = "Value", referenceId = referenceId))

        // Then: Navigate flow is called
        assertEquals(referenceId, awaitItem())
      }
    }

  @Test
  fun testOnClickFileRowSuccess() =
    runTest {
      // Given document reference mgo resource is stored
      val referenceId = setDocumentReferenceMgoResource()

      // Given: Fhir binary download is success
      fhirRepository.setFetchBinaryResult(Result.success(FhirBinary(file = File(""), contentType = "application/pdf")))

      // Given: viewmodel
      val viewModel =
        createViewModel(
          organization =
            TEST_MGO_ORGANIZATION.copy(
              dataServices =
                listOf(
                  nl.rijksoverheid.mgo.component.organization.TEST_DOCUMENTS_DATA_SERVICE,
                ),
            ),
          referenceId = referenceId,
          isSummary = true,
        )

      // When: Calling onClickFileRow
      val row =
        viewModel.viewState.value.sections[1]
          .rows
          .first() as UISchemaRow.Binary.NotDownloaded
      viewModel.onClickFileRow(row = row)

      // Then: View state is updated
      viewModel.viewState.test {
        Assert.assertTrue(
          awaitItem()
            .sections[1]
            .rows
            .first() is UISchemaRow.Binary.Downloaded,
        )
      }
    }

  @Test
  fun testOnClickFileRowFailure() =
    runTest {
      // Given document reference mgo resource is stored
      val referenceId = setDocumentReferenceMgoResource()

      // Given: Fhir binary download failed
      fhirRepository.setFetchBinaryResult(Result.failure(IllegalStateException("Something went wrong")))

      // Given: viewmodel
      val viewModel =
        createViewModel(
          organization =
            TEST_MGO_ORGANIZATION.copy(
              dataServices =
                listOf(
                  nl.rijksoverheid.mgo.component.organization.TEST_DOCUMENTS_DATA_SERVICE,
                ),
            ),
          referenceId = referenceId,
          isSummary = true,
        )

      // When: Calling onClickFileRow
      val row =
        viewModel.viewState.value.sections[1]
          .rows
          .first() as UISchemaRow.Binary.NotDownloaded
      viewModel.onClickFileRow(row = row)

      // Then: View state is updated
      viewModel.viewState.test {
        Assert.assertTrue(
          awaitItem()
            .sections[1]
            .rows
            .first() is UISchemaRow.Binary.NotDownloaded,
        )
      }
    }

  private suspend fun setAlcoholUseMgoResource(): MgoResourceReferenceId {
    val mgoResource = mgoResourceParser.invoke(fhirResponse = readResourceFile("alcoholUse.json"), fhirVersion = FhirVersion.R3).first()
    mgoResourceStore.store(mgoResource)
    return mgoResource.referenceId
  }

  private suspend fun setDocumentReferenceMgoResource(): MgoResourceReferenceId {
    val mgoResource = mgoResourceParser.invoke(fhirResponse = readResourceFile("documentReference.json"), fhirVersion = FhirVersion.R3).first()
    mgoResourceStore.store(mgoResource)
    return mgoResource.referenceId
  }

  private fun createViewModel(
    organization: MgoOrganization,
    referenceId: MgoResourceReferenceId,
    isSummary: Boolean,
  ): UiSchemaScreenViewModel =
    UiSchemaScreenViewModel(
      organization = organization,
      referenceId = referenceId,
      isSummary = isSummary,
      fhirRepository = fhirRepository,
      uiSchemaSectionMapper = uiSchemaSectionMapper,
      uiSchemaParser = uiSchemaParser,
      mgoResourceStore = mgoResourceStore,
      dvaApiBaseUrl = "",
      ioDispatcher = mainDispatcherRule.testDispatcher,
    )
}
