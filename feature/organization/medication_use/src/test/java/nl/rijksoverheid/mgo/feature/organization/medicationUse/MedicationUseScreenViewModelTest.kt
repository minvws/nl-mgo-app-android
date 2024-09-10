package nl.rijksoverheid.mgo.feature.organization.medicationUse

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.TestUiSchemaRepository
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheCategory
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheKey
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MedicationUseScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given medications, When creating viewmodel, List items are shown`() =
        runTest {
            // Given
            val organizationRepository = TestOrganizationRepository()
            organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))
            val uiSchemaRepository = TestUiSchemaRepository()
            uiSchemaRepository.store(
                key =
                    UiSchemaCacheKey(
                        organizationId = TEST_MGO_ORGANIZATION.id,
                        category =
                            UiSchemaCacheCategory
                                .MEDICATION_USE,
                    ),
                uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION),
            )

            // When
            val viewModel =
                MedicationUseScreenViewModel(organizationRepository = organizationRepository, uiSchemaRepository = uiSchemaRepository)

            // Then
            viewModel.viewState.test {
                assertEquals(listOf(TEST_LIST_ITEM_1), awaitItem().listItems)
            }
        }
}
