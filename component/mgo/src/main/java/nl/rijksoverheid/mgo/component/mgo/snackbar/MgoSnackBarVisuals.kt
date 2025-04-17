package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

typealias MgoSnackBarDataIcon = Int

/**
 * Determines the appearance of a [MgoSnackBar].
 * @param type The type of snackbar you want to display.
 * @param title The title of the snackbar.
 * @param action Show clickable action text if not null.
 * @param actionCallback Called when clicked on the action text.
 * @param actionLabel Not used.
 * @param duration Not used.
 * @param message Not used.
 * @param withDismissAction Not used.
 */
data class MgoSnackBarVisuals(
  val type: MgoSnackBarType,
  @StringRes val title: Int,
  @StringRes val action: Int? = null,
  val actionCallback: (suspend () -> Unit)? = null,
  override val actionLabel: String? = null,
  override val duration: SnackbarDuration = SnackbarDuration.Short,
  override val message: String = "",
  override val withDismissAction: Boolean = false,
) : SnackbarVisuals
