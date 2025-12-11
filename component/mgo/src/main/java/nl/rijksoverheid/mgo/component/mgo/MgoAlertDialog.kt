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
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.BackgroundsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesCritical

object MgoAlertDialogTestTag {
  const val CONFIRM_BUTTON = "MgoAlertDialogConfirmButton"
  const val DISMISS_BUTTON = "MgoAlertDialogDismissButton"
}

@Composable
fun MgoAlertDialog(
  onDismissRequest: () -> Unit,
  positiveButtonText: String,
  positiveButtonTextColor: Color = MaterialTheme.colorScheme.StatesCritical(),
  onClickPositiveButton: () -> Unit,
  negativeButtonText: String? = null,
  negativeButtonTextColor: Color = MaterialTheme.colorScheme.ActionsGhostText(),
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
    containerColor = MaterialTheme.colorScheme.BackgroundsSecondary(),
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
