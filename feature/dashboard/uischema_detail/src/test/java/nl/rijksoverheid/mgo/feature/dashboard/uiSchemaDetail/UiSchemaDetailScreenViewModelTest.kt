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
        expectedList: List<AttachmentState>,
    ) {
        attachmentStates.test {
            // When: Calling onDownloadAttachment
            onDownloadAttachment(uiEntry)

            // Then: sequentially: not downloaded, loading and downloaded states are emitted
            if (expectedList.isEmpty()) {
                assertEquals(awaitItem(), listOf<AttachmentState>())
            } else {
                for (expected in expectedList) {
                    assertEquals(awaitItem(), listOf(expected))
                }
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
                expectedList =
                    listOf(
                        AttachmentState.NotDownloaded(label = "UI Entry Label", url = "example.pdf"),
                        AttachmentState.Loading(label = "UI Entry Label"),
                        AttachmentState.Downloaded(label = "UI Entry Label", file = File(""), contentType = ""),
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
                expectedList =
                    listOf(
                        AttachmentState.NotDownloaded(label = "UI Entry Label", url = "example.pdf"),
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
                expectedList =
                    listOf(
                        AttachmentState.NotDownloaded(label = "UI Entry Label", url = ""),
                        AttachmentState.Loading(label = "UI Entry Label"),
                        AttachmentState.Empty(label = "UI Entry Label"),
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
                expectedList = listOf(),
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
                expectedList =
                    listOf(
                        AttachmentState.NotDownloaded(label = "UI Entry Label", url = "example.pdf"),
                        AttachmentState.Loading(label = "UI Entry Label"),
                        AttachmentState.Error(label = "UI Entry Label", error = error),
                    ),
            )
        }
}
