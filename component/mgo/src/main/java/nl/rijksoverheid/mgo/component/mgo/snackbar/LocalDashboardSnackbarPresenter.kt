package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Implementation of [LocalDashboardSnackbarPresenter].
 */
class DefaultLocalDashboardSnackbarPresenter {
  private val _snackbarVisuals = MutableSharedFlow<MgoSnackBarVisuals>(extraBufferCapacity = 1)
  val snackbarVisuals = _snackbarVisuals.asSharedFlow()

  fun showSnackbar(visuals: MgoSnackBarVisuals) {
    _snackbarVisuals.tryEmit(visuals)
  }
}

/**
 * Use to show a snackbar whenever the dashboard is visible on screen.
 */
val LocalDashboardSnackbarPresenter = compositionLocalOf { DefaultLocalDashboardSnackbarPresenter() }
