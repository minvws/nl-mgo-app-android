package nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.TEST_MGO_LABORATORY_TEST_RESULT
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.TestLaboratoryTestResultRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class LaboratoryTestResultScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given test results, When creating viewmodel, View state is updated`() =
        runTest {
            // Given
            val testResults = listOf(TEST_MGO_LABORATORY_TEST_RESULT)
            val testLaboratoryResultRepository = TestLaboratoryTestResultRepository(Result.success(testResults))

            // When
            val viewModel =
                LaboratoryTestResultScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    laboratoryTestResultRepository = testLaboratoryResultRepository,
                )

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(testResults, awaitItem().testResults)
            }
        }

    @Test
    fun `Given error, When creating viewmodel, View state is updated`() =
        runTest {
            // Given
            val error = IllegalStateException("something went wrong")
            val testLaboratoryResultRepository = TestLaboratoryTestResultRepository(Result.failure(error))

            // When
            val viewModel =
                LaboratoryTestResultScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    laboratoryTestResultRepository = testLaboratoryResultRepository,
                )

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(error, awaitItem().error)
            }
        }
}
