package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalDashboardSnackbarPresenterTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testPresentAndConsume() =
    runTest {
      // Given: The snackbar presenter
      val snackbarPresenter = DefaultLocalDashboardSnackbarPresenter()

      snackbarPresenter.snackbarVisuals.test {
        // When: Calling present
        snackbarPresenter.showSnackbar(MgoSnackBarVisuals(type = MgoSnackBarType.INFO, title = 0))

        // Then: Visuals that are presented are returned
        assertEquals(awaitItem(), MgoSnackBarVisuals(type = MgoSnackBarType.INFO, title = 0))
      }
    }

  @Test
  fun testCompositionLocal() {
    // Given The snackbar presenter
    val snackbarPresenter = DefaultLocalDashboardSnackbarPresenter()

    // Given: A composable with the composition local provider setup
    composeTestRule.setContent {
      CompositionLocalProvider(LocalDashboardSnackbarPresenter provides snackbarPresenter) {
        // When: Getting the presenter
        val providedPresenter = LocalDashboardSnackbarPresenter.current

        // Then: Presenter is the same as the one provided through the composition local provider
        assertEquals(snackbarPresenter, providedPresenter)
      }
    }
  }
}
