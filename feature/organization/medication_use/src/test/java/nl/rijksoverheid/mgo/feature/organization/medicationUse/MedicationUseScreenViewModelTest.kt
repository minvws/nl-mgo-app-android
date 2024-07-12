package nl.rijksoverheid.mgo.feature.organization.medicationUse

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.medication.models.TEST_MGO_MEDICATION
import nl.rijksoverheid.mgo.data.medication.models.TestMedicationRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MedicationUseScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given medications, When creating viewmodel, View state is updated`() =
        runTest {
            // Given
            val medications = listOf(TEST_MGO_MEDICATION)
            val testLaboratoryResultRepository = TestMedicationRepository(Result.success(medications))

            // When
            val viewModel =
                MedicationUseScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    medicationRepository = testLaboratoryResultRepository,
                )

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(medications, awaitItem().medications)
            }
        }

    @Test
    fun `Given error, When creating viewmodel, View state is updated`() =
        runTest {
            // Given
            val error = IllegalStateException("something went wrong")
            val testLaboratoryResultRepository = TestMedicationRepository(Result.failure(error))

            // When
            val viewModel =
                MedicationUseScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    medicationRepository = testLaboratoryResultRepository,
                )

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(error, awaitItem().error)
            }
        }
}
