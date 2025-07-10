package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText

object MgoAlertDialogTestTag {
  const val CONFIRM_BUTTON = "MgoAlertDialogConfirmButton"
  const val DISMISS_BUTTON = "MgoAlertDialogDismissButton"
}

/**
 * Composable that shows a alert dialog.
 * @param onDismissRequest Called when the dialog has been dismissed.
 * @param positiveButtonText The text of the positive button.
 * @param positiveButtonTextColor The color of the positive button. Defaults to [MaterialTheme.colorScheme.actionTertiaryNegativeText].
 * @param onClickPositiveButton Called when clicking on the positive button.
 * @param negativeButtonText text of the negative button.
 * @param negativeButtonTextColor The color of the positive button. Defaults to [MaterialTheme.colorScheme.actionTertiaryDefaultText].
 * @param onClickPositiveButton Called when clicking on the negative button.
 * @param heading The title of the dialog.
 * @param subHeading The subheading of the dialog.
 */
@Composable
fun MgoAlertDialog(
  onDismissRequest: () -> Unit,
  positiveButtonText: String,
  positiveButtonTextColor: Color = MaterialTheme.colorScheme.interactiveTertiaryCriticalText(),
  onClickPositiveButton: () -> Unit,
  negativeButtonText: String? = null,
  negativeButtonTextColor: Color = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
  onClickNegativeButton: (() -> Unit)? = null,
  heading: String,
  subHeading: String,
) {
  AlertDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = {
      TextButton(
        modifier = Modifier.testTag(MgoAlertDialogTestTag.CONFIRM_BUTTON),
        colors = ButtonDefaults.textButtonColors(contentColor = positiveButtonTextColor),
        onClick = onClickPositiveButton,
      ) {
        Text(
          text = positiveButtonText,
          fontWeight = FontWeight.Bold,
        )
      }
    },
    dismissButton = {
      if (negativeButtonText != null && onClickNegativeButton != null) {
        TextButton(
          modifier = Modifier.testTag(MgoAlertDialogTestTag.DISMISS_BUTTON),
          colors =
            ButtonDefaults.textButtonColors(
              contentColor = negativeButtonTextColor,
            ),
          onClick = onClickNegativeButton,
        ) {
          Text(
            text = negativeButtonText,
            fontWeight = FontWeight.Bold,
          )
        }
      }
    },
    title = {
      Text(heading, style = MaterialTheme.typography.headlineSmall)
    },
    text = {
      Text(subHeading, style = MaterialTheme.typography.bodyMedium)
    },
    containerColor = MaterialTheme.colorScheme.backgroundSecondary(),
  )
}

@PreviewLightDark
@Composable
internal fun MgoAlertDialogPreview() {
  MgoTheme {
    MgoAlertDialog(
      onDismissRequest = {},
      positiveButtonText = "Ok",
      onClickPositiveButton = {},
      negativeButtonText = "Cancel",
      onClickNegativeButton = {},
      heading = "Title",
      subHeading = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    )
  }
}
