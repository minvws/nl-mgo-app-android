package nl.rijksoverheid.mgo.devicerooted

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.framework.copy.R
import java.util.Locale

/**
 * Composable that shows a dialog to inform the user the device has been rooted.
 * @param show True if the dialog needs to be shown.
 */
@Composable
fun DeviceRootedDialog(show: Boolean) {
  var showDialog by remember { mutableStateOf(show) }
  if (showDialog) {
    MgoAlertDialog(
      heading = stringResource(id = R.string.launch_jailbreak_heading),
      subHeading = stringResource(id = R.string.launch_jailbreak_subheading),
      onDismissRequest = { showDialog = false },
      positiveButtonText =
        stringResource(id = R.string.common_ok)
          .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
      onClickPositiveButton = { showDialog = false },
      positiveButtonTextColor = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
    )
  }
}

@PreviewLightDark
@Composable
internal fun DeviceRootedDialogPreview() {
  MgoTheme {
    DeviceRootedDialog(show = true)
  }
}
