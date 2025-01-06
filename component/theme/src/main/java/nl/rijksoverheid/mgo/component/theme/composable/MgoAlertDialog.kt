package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionTertiaryNegativeText
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.headingSmall

@Composable
fun MgoAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButtonText: String,
    confirmButtonColor: Color = MaterialTheme.colorScheme.actionTertiaryNegativeText(),
    onClickConfirmButton: () -> Unit,
    dismissButtonText: String? = null,
    dismissButtonColor: Color = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
    onClickDismissButton: (() -> Unit)? = null,
    title: String,
    text: String,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = confirmButtonColor),
                onClick = onClickConfirmButton,
            ) {
                Text(
                    text = confirmButtonText,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        dismissButton = {
            if (dismissButtonText != null && onClickDismissButton != null) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = dismissButtonColor),
                    onClick = onClickDismissButton,
                ) {
                    Text(
                        text = dismissButtonText,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        },
        title = {
            Text(title, style = MaterialTheme.typography.headingSmall)
        },
        text = {
            Text(text, style = MaterialTheme.typography.bodySmall)
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
            confirmButtonText = "Ok",
            onClickConfirmButton = {},
            dismissButtonText = "Cancel",
            onClickDismissButton = {},
            title = "Title",
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        )
    }
}
