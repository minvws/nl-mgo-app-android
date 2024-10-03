package nl.rijksoverheid.mgo.component.snackbar

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

typealias MgoSnackBarDataIcon = Int

data class MgoSnackBarVisuals(
    val type: MgoSnackBarType,
    @StringRes val title: Int,
    @StringRes val action: Int? = null,
    val actionCallback: (suspend () -> Unit)? = null,
    override val actionLabel: String? = null, // Not used
    override val duration: SnackbarDuration = SnackbarDuration.Short, // Not used
    override val message: String = "", // Not used
    override val withDismissAction: Boolean = false, // Not used
) : SnackbarVisuals

val TEST_MGO_SNACK_BAR_VISUALS = MgoSnackBarVisuals(type = MgoSnackBarType.SUCCESS, title = -1)
