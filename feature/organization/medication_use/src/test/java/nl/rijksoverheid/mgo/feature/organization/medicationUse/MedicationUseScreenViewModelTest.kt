package nl.rijksoverheid.mgo.feature.organization.medicationUse

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.medication.models.TestMedicationRepository
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MedicationUseScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given medications, When creating viewmodel, Ui schemas are fetched`() =
        runTest {
            // Given
            val testLaboratoryResultRepository = TestMedicationRepository(Result.success(listOf(TEST_UI_SCHEMA_MEDICATION)))

            // When
            val viewModel =
                MedicationUseScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    medicationRepository = testLaboratoryResultRepository,
                )

            // Then
            viewModel.viewState.test {
                assertEquals(listOf(TEST_UI_SCHEMA_MEDICATION), awaitItem().uiSchemaList)
            }
        }
}
