package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalSnackbarPresenterTest {
    @get:Rule
    val composeTestRule = createComposeRule()

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

    @Test
    fun testNoVisuals() {
        // Given: The snackbar presenter
        val snackbarPresenter = DefaultLocalSnackBarPresenter()

        // When: Calling consume
        val visuals = snackbarPresenter.consume()

        // Then: No visuals are returned
        assertNull(visuals)
    }

    @Test
    fun testCompositionLocal() {
        // Given The snackbar presenter
        val snackbarPresenter = DefaultLocalSnackBarPresenter()

        // Given: A composable with the composition local provider setup
        composeTestRule.setContent {
            CompositionLocalProvider(LocalSnackBarPresenter provides snackbarPresenter) {
                // When: Getting the presenter
                val providedPresenter = LocalSnackBarPresenter.current

                // Then: Presenter is the same as the one provided through the composition local provider
                assertEquals(snackbarPresenter, providedPresenter)
            }
        }
    }
}
