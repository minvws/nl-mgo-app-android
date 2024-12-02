package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinary
import nl.rijksoverheid.mgo.data.healthcare.binary.TestHealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_ENTRY
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_GROUP
import nl.rijksoverheid.mgo.data.uiSchema.UIEntryType
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import kotlinx.coroutines.test.runTest

internal class UiSchemaDetailScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareBinaryRepository = TestHealthCareBinaryRepository()

    @Before
    fun setUp() {
        healthCareBinaryRepository.reset()
    }

    @Test
    fun `Given ui entry with url, When calling onDownloadAttachment, Then return correct attachment state`() =
        runTest {
            // Given: Organization with document data service type
            val organization =
                TEST_MGO_ORGANIZATION.copy(
                    dataServices =
                        listOf(
                            MgoOrganizationDataService(
                                resourceEndpoint = "endpoint",
                                type = MgoOrganizationDataServiceType.DOCUMENTS,
                            ),
                        ),
                )

            // Given: ui entry with url
            val uiEntry = TEST_UI_ENTRY.copy(url = "example.pdf", type = UIEntryType.DownloadLink)

            // Given: ui schema
            val uiSchema =
                TEST_UI_SCHEMA.copy(
                    children = listOf(TEST_UI_SCHEMA_GROUP.copy(children = listOf(uiEntry))),
                )

            // Given: Mock download to be success
            healthCareBinaryRepository.setDownloadResult(Result.success(HealthCareBinary(file = File(""), contentType = "")))

            // Given: viewmodel
            val viewModel =
                UiSchemaDetailScreenViewModel(
                    organization = organization,
                    uiSchema = uiSchema,
                    healthCareBinaryRepository = healthCareBinaryRepository,
                )

            // When: Calling onDownloadAttachment
            viewModel.onDownloadAttachment(uiEntry)

            // Then: attachment state is updated
            viewModel.attachmentStates.test {
                val emit = awaitItem()
                Assert.assertEquals(1, emit.size)
            }
        }
}
