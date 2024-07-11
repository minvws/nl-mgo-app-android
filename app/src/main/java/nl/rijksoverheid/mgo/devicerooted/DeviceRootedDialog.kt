package nl.rijksoverheid.mgo.devicerooted

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun DeviceRootedDialog(show: Boolean) {
    var showDialog by remember { mutableStateOf(show) }
    if (showDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.launch_jailbreak_heading)) },
            text = { Text(text = stringResource(id = R.string.launch_jailbreak_subheading)) },
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                ) {
                    Text(stringResource(id = R.string.common_ok))
                }
            },
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
