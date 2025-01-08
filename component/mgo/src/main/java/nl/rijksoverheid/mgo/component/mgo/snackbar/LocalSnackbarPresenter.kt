package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.compose.runtime.compositionLocalOf

class DefaultLocalSnackbarPresenter {
    private var visuals: MgoSnackBarVisuals? = null

    fun get(): MgoSnackBarVisuals? {
        return this.visuals
    }

    fun present(visuals: MgoSnackBarVisuals) {
        this.visuals = visuals
    }

    fun consume(): MgoSnackBarVisuals? {
        if (visuals == null) return null
        return visuals.also { this.visuals = null }
    }
}

val LocalSnackbarPresenter = compositionLocalOf { DefaultLocalSnackbarPresenter() }
