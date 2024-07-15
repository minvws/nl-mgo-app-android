package nl.rijksoverheid.mgo.feature.organization.problems

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.concern.models.TEST_MGO_CONCERN
import nl.rijksoverheid.mgo.data.concern.models.TestConcernRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class ProblemsScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given concerns, When creating viewmodel, View state is updated`() =
        runTest {
            // Given
            val concerns = listOf(TEST_MGO_CONCERN)
            val concernRepository = TestConcernRepository(Result.success(concerns))

            // When
            val viewModel =
                ProblemsScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    concernRepository = concernRepository,
                )

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(concerns, awaitItem().concerns)
            }
        }

    @Test
    fun `Given error, When creating viewmodel, View state is updated`() =
        runTest {
            // Given
            val error = IllegalStateException("something went wrong")
            val concernRepository = TestConcernRepository(Result.failure(error))

            // When
            val viewModel =
                ProblemsScreenViewModel(
                    provider = TEST_MGO_ORGANIZATION,
                    concernRepository = concernRepository,
                )

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(error, awaitItem().error)
            }
        }
}
