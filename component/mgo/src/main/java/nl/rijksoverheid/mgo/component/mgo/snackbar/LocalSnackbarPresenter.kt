package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.compose.runtime.compositionLocalOf

/**
 * This class is used to display a Snackbar. It exists so that you can easily show a Snackbar from within a ViewModel
 * without knowing exactly where the actual Snackbar UI Component is located. Currently, it does not support recomposition.
 * That means when you call [present] and you [consume] in the same screen, the Snackbar will not be shown. It will however work
 * if you call [present] in Screen B and then go back and call [consume] in Screen A. That is the only use case for the Snackbar
 * in this application as of yet.
 */
class DefaultLocalSnackBarPresenter {
  private var visuals: MgoSnackBarVisuals? = null

  /**
   * Call this to show a Snackbar where [consume] is called.
   */
  fun present(visuals: MgoSnackBarVisuals) {
    this.visuals = visuals
  }

  /**
   * Get information needed to show a Snackbar. Information can be set with [present].
   */
  fun consume(): MgoSnackBarVisuals? {
    if (visuals == null) return null
    return visuals.also { this.visuals = null }
  }
}

/**
 * This can be used to manage snackbar state within a Jetpack Compose hierarchy.
 */
val LocalSnackBarPresenter = compositionLocalOf { DefaultLocalSnackBarPresenter() }
