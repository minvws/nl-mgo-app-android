package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.shared.TEST_UI_SCHEMA
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.binary.TestFhirBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.TestMgoResourceRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaScreenViewModel
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class UiSchemaScreenViewModelTest {
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

    private val healthCareBinaryRepository = TestFhirBinaryRepository()

    @Test
    fun testUiSchema() =
        runTest {
            // When: creating view model
            val viewModel = getViewModel()

            // Then: ui schema is set
            viewModel.uiSchema.test {
                assertEquals(TEST_UI_SCHEMA, awaitItem())
            }
        }

    private fun getViewModel(): UiSchemaScreenViewModel {
        return UiSchemaScreenViewModel(
            organization = TEST_MGO_ORGANIZATION,
            mgoResource = TEST_MGO_RESOURCE,
            fhirBinaryRepository = healthCareBinaryRepository,
            isSummary = false,
            uiSchemaMapper = TestUiSchemaMapper(),
            mgoResourceRepository = TestMgoResourceRepository(),
        )
    }
}
