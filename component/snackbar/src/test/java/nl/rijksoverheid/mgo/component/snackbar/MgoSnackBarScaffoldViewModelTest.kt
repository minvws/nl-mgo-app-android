package nl.rijksoverheid.mgo.component.snackbar

import app.cash.turbine.test
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MgoSnackBarScaffoldViewModelTest {
    @Test
    fun `Given repository with visuals, When ViewModel is initialized, Then visuals are emitted`() =
        runTest {
            // Given
            val repository = DefaultSnackBarRepository()
            val visuals = TEST_MGO_SNACK_BAR_VISUALS
            repository.show(visuals)

            // When
            val viewModel = MgoSnackBarScaffoldViewModel(snackBarRepository = repository)

            // Then
            viewModel.visuals.test {
                assertEquals(visuals, awaitItem())
            }
        }
}
