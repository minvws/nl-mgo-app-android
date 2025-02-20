package nl.rijksoverheid.mgo.component.mgo.snackbar

import org.junit.Assert.assertEquals
import org.junit.Test

class LocalSnackbarPresenterTest {
    @Test
    fun testPresentAndConsume() {
        // Given: The snackbar presenter
        val snackbarPresenter = DefaultLocalSnackBarPresenter()

        // When: Calling present
        snackbarPresenter.present(MgoSnackBarVisuals(type = MgoSnackBarType.INFO, title = 0))

        // When: Consuming
        val visuals = snackbarPresenter.consume()

        // Then: Visuals that are presented are returned
        assertEquals(visuals, MgoSnackBarVisuals(type = MgoSnackBarType.INFO, title = 0))
    }
}
