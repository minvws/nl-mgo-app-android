package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.TEST_UI_ENTRY_BINARY
import nl.rijksoverheid.mgo.data.fhirParser.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.fhirParser.TEST_UI_SCHEMA_GROUP
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.binary.TEST_FHIR_BINARY
import nl.rijksoverheid.mgo.data.healthcare.binary.TestFhirBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.TestMgoResourceRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_DOCUMENTS_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaSection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class UiSchemaScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  private val uiSchemaMapper = TestUiSchemaMapper()
  private val mgoResourceRepository = TestMgoResourceRepository()
  private val fhirBinaryRepository = TestFhirBinaryRepository()

  @Test
  fun testViewState() =
    runTest {
      // When: creating view model
      val viewModel = getViewModel()

      // Then: View state is updated
      val expectedViewState =
        UiSchemaScreenViewState(
          toolbarTitle = "UI Schema Label",
          sections =
            listOf(
              UISchemaSection(
                heading = "UI Schema Group",
                rows =
                  listOf(
                    UISchemaRow.Static(heading = "UI Entry Label", value = "Display"),
                  ),
              ),
            ),
        )

      viewModel.viewState.test {
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun testFileEmpty() =
    runTest {
      // Given: UI Schema has download without url
      val uiSchema =
        TEST_UI_SCHEMA.copy(
          children =
            listOf(
              TEST_UI_SCHEMA_GROUP.copy(
                children =
                  listOf(
                    TEST_UI_ENTRY_BINARY.copy
                      (reference = null),
                  ),
              ),
            ),
        )
      uiSchemaMapper.setDetail(uiSchema)

      // When: creating view model
      val viewModel = getViewModel()

      // Then: View state is updated
      val expectedViewState =
        UiSchemaScreenViewState(
          toolbarTitle = "UI Schema Label",
          sections =
            listOf(
              UISchemaSection(
                heading = "UI Schema Group",
                rows =
                  listOf(
                    UISchemaRow.Binary.Empty(heading = null, value = "UI Entry Label"),
                  ),
              ),
            ),
        )

      viewModel.viewState.test {
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun testOnClickReferenceRow() =
    runTest {
      // Given: Row
      val row = UISchemaRow.Reference(heading = "Heading", value = "Value", referenceId = "1")

      // Given: Reference exists in store
      mgoResourceRepository.setMgoResource(Result.success(TEST_MGO_RESOURCE))

      // Given: Viewmodel
      val viewModel = getViewModel()

      // When: Calling onClickReferenceRow
      viewModel.navigate.test {
        viewModel.onClickReferenceRow(row)

        // Then: Navigate with mgo resource
        assertEquals(TEST_MGO_RESOURCE, awaitItem())
      }
    }

  @Test
  fun testOnClickFileRowSuccess() =
    runTest {
      // Given: Row
      val row = UISchemaRow.Binary.NotDownloaded.Idle(heading = null, value = "UI Entry Label", binary = "")

      // Given: Download succeeds
      fhirBinaryRepository.setDownloadResult(Result.success(TEST_FHIR_BINARY))

      // Given: Row is added to view state
      val uiSchema =
        TEST_UI_SCHEMA.copy(
          children =
            listOf(
              TEST_UI_SCHEMA_GROUP.copy(
                children =
                  listOf(
                    TEST_UI_ENTRY_BINARY,
                  ),
              ),
            ),
        )
      uiSchemaMapper.setDetail(uiSchema)

      // Given: Viewmodel
      val viewModel = getViewModel()

      // When: Calling onClickFileRow
      viewModel.viewState.test {
        viewModel.onClickFileRow(row)

        // Then: Initial state is idle
        assertTrue(awaitItem().sections.first().rows.first() is UISchemaRow.Binary.NotDownloaded)

        // Then: Next state is loading
        assertTrue(awaitItem().sections.first().rows.first() is UISchemaRow.Binary.Loading)

        // Then: Final state is downloaded
        assertTrue(awaitItem().sections.first().rows.first() is UISchemaRow.Binary.Downloaded)
      }
    }

  @Test
  fun testOnClickFileRowError() =
    runTest {
      // Given: Row
      val row = UISchemaRow.Binary.NotDownloaded.Idle(heading = null, value = "UI Entry Label", binary = "")

      // Given: Download succeeds
      fhirBinaryRepository.setDownloadResult(Result.failure(IllegalStateException("Something went wrong")))

      // Given: Row is added to view state
      val uiSchema =
        TEST_UI_SCHEMA.copy(
          children =
            listOf(
              TEST_UI_SCHEMA_GROUP.copy(
                children =
                  listOf(
                    TEST_UI_ENTRY_BINARY,
                  ),
              ),
            ),
        )
      uiSchemaMapper.setDetail(uiSchema)

      // Given: Viewmodel
      val viewModel = getViewModel()

      // When: Calling onClickFileRow
      viewModel.viewState.test {
        viewModel.onClickFileRow(row)

        // Then: Initial state is idle
        assertTrue(awaitItem().sections.first().rows.first() is UISchemaRow.Binary.NotDownloaded)

        // Then: Next state is loading
        assertTrue(awaitItem().sections.first().rows.first() is UISchemaRow.Binary.Loading)

        // Then: Final state is error
        assertTrue(awaitItem().sections.first().rows.first() is UISchemaRow.Binary.NotDownloaded.Error)
      }
    }

  private fun getViewModel(): UiSchemaScreenViewModel {
    return UiSchemaScreenViewModel(
      organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_DOCUMENTS_DATA_SERVICE)),
      mgoResource = TEST_MGO_RESOURCE,
      fhirBinaryRepository = fhirBinaryRepository,
      isSummary = false,
      uiSchemaMapper = uiSchemaMapper,
      mgoResourceRepository = mgoResourceRepository,
    )
  }
}
