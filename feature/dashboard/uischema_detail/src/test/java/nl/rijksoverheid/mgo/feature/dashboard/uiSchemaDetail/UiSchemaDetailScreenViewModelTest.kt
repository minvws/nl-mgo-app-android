package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinary
import nl.rijksoverheid.mgo.data.healthcare.binary.TestHealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_ENTRY_DOWNLOAD_LINK
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_GROUP
import nl.rijksoverheid.mgo.data.uiSchema.UIEntry
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.lang.IllegalStateException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest

internal class UiSchemaDetailScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val organizationWithoutDocument =
        TEST_MGO_ORGANIZATION.copy(
            dataServices = listOf(),
        )

    private val organizationWithDocument =
        TEST_MGO_ORGANIZATION.copy(
            dataServices =
                listOf(
                    MgoOrganizationDataService(
                        resourceEndpoint = "endpoint",
                        type = MgoOrganizationDataServiceType.DOCUMENTS,
                    ),
                ),
        )

    private val healthCareBinaryRepository = TestHealthCareBinaryRepository()

    private fun getViewModel(
        uiEntry: UIEntry,
        organization: MgoOrganization,
    ): UiSchemaDetailScreenViewModel {
        return UiSchemaDetailScreenViewModel(
            organization = organization,
            uiSchema =
                TEST_UI_SCHEMA.copy(
                    children =
                        listOf(
                            TEST_UI_SCHEMA_GROUP.copy(
                                children =
                                    listOf(
                                        uiEntry,
                                        TEST_UI_ENTRY_DOWNLOAD_LINK.copy(
                                            label = "UI Entry Label #2",
                                        ),
                                    ),
                            ),
                        ),
                ),
            healthCareBinaryRepository = healthCareBinaryRepository,
        )
    }

    private suspend fun UiSchemaDetailScreenViewModel.downloadAndAssert(
        uiEntry: UIEntry,
        expectedStates: List<AttachmentState>,
    ) {
        attachmentsState.map { it[uiEntry] }.test {
            // When: Calling onDownloadAttachment
            onDownloadAttachment(uiEntry)

            // Then: Expect emitted states
            for (expectedState in expectedStates) {
                assertEquals(expectedState, awaitItem())
            }
        }
    }

    @Before
    fun setUp() {
        healthCareBinaryRepository.reset()
    }

    @Test
    fun testDownloadAttachmentUiEntryWithUrl() =
        runTest {
            // Given: ui entry with url
            val uiEntry = TEST_UI_ENTRY_DOWNLOAD_LINK

            // Given: Mock download to be success
            healthCareBinaryRepository.setDownloadResult(Result.success(HealthCareBinary(file = File(""), contentType = "")))

            // Given: viewmodel
            val viewModel = getViewModel(uiEntry = uiEntry, organization = organizationWithDocument)

            // When: Download attachment
            // Then: Assert attachment states
            viewModel.downloadAndAssert(
                uiEntry = uiEntry,
                expectedStates =
                    listOf(
                        AttachmentState.NotDownloaded,
                        AttachmentState.Loading,
                        AttachmentState.Downloaded(binary = HealthCareBinary(file = File(""), contentType = "")),
                    ),
            )
        }

    @Test
    fun testDownloadAttachmentNoDocumentDataServiceType() =
        runTest {
            // Given: ui entry with url
            val uiEntry = TEST_UI_ENTRY_DOWNLOAD_LINK

            // Given: Mock download to be success
            healthCareBinaryRepository.setDownloadResult(Result.success(HealthCareBinary(file = File(""), contentType = "")))

            // Given: viewmodel
            val viewModel = getViewModel(uiEntry = uiEntry, organization = organizationWithoutDocument)

            // When: Download attachment
            // Then: Assert attachment states
            viewModel.downloadAndAssert(
                uiEntry = uiEntry,
                expectedStates =
                    listOf(
                        AttachmentState.NotDownloaded,
                    ),
            )
        }

    @Test
    fun testDownloadAttachmentUiEntryWithoutUrl() =
        runTest {
            // Given: ui entry with empty url
            val uiEntry = TEST_UI_ENTRY_DOWNLOAD_LINK.copy(url = "")

            // Given: Mock download to be success
            healthCareBinaryRepository.setDownloadResult(Result.success(HealthCareBinary(file = File(""), contentType = "")))

            // Given: viewmodel
            val viewModel = getViewModel(uiEntry = uiEntry, organization = organizationWithDocument)

            // When: Download attachment
            // Then: Assert attachment states
            viewModel.downloadAndAssert(
                uiEntry = uiEntry,
                expectedStates =
                    listOf(
                        AttachmentState.NotDownloaded,
                        AttachmentState.Loading,
                        AttachmentState.Empty,
                    ),
            )
        }

    @Test
    fun testDownloadAttachmentUiEntryNullUrl() =
        runTest {
            // Given: ui entry with null url
            val uiEntry = TEST_UI_ENTRY_DOWNLOAD_LINK.copy(url = null)

            // Given: Mock download to be success
            healthCareBinaryRepository.setDownloadResult(Result.success(HealthCareBinary(file = File(""), contentType = "")))

            // Given: viewmodel
            val viewModel = getViewModel(uiEntry = uiEntry, organization = organizationWithDocument)

            // When: Download attachment
            // Then: Assert attachment states
            viewModel.downloadAndAssert(
                uiEntry = uiEntry,
                expectedStates =
                    listOf(
                        AttachmentState.NotDownloaded,
                    ),
            )
        }

    @Test
    fun testDownloadAttachmentFailed() =
        runTest {
            // Given: ui entry with url
            val uiEntry = TEST_UI_ENTRY_DOWNLOAD_LINK

            // Given: Mock download to fail
            val error = IllegalStateException("Something went wrong")
            healthCareBinaryRepository.setDownloadResult(Result.failure(error))

            // Given: viewmodel
            val viewModel = getViewModel(uiEntry = uiEntry, organization = organizationWithDocument)

            // When: Download attachment
            // Then: Assert attachment states
            viewModel.downloadAndAssert(
                uiEntry = uiEntry,
                expectedStates =
                    listOf(
                        AttachmentState.NotDownloaded,
                        AttachmentState.Loading,
                        AttachmentState.Error(error),
                    ),
            )
        }
}
