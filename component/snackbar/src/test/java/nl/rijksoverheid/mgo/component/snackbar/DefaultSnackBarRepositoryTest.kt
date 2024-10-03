package nl.rijksoverheid.mgo.component.snackbar

import app.cash.turbine.test
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultSnackBarRepositoryTest {
    @Test
    fun `Given repository, When calling show, The visuals are emitted`() =
        runTest {
            // Given
            val repository = DefaultSnackBarRepository()
            val visuals = MgoSnackBarVisuals(type = MgoSnackBarType.SUCCESS, title = -1)

            // When
            repository.show(visuals)

            // Then
            repository.get().test {
                assertEquals(visuals, awaitItem())
            }
        }

    @Test
    fun `Given repository, When calling dismiss, The visuals are no longer emitted`() =
        runTest {
            // Given
            val repository = DefaultSnackBarRepository()
            val visuals = TEST_MGO_SNACK_BAR_VISUALS

            // When
            repository.show(visuals)
            repository.dismiss()

            // Then
            repository.get().test {
                expectNoEvents()
            }
        }
}
