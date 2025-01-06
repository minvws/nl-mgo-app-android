package nl.rijksoverheid.mgo.devicerooted

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.composable.MgoAlertDialog
import nl.rijksoverheid.mgo.framework.copy.R
import java.util.Locale

@Composable
fun DeviceRootedDialog(show: Boolean) {
    var showDialog by remember { mutableStateOf(show) }
    if (showDialog) {
        MgoAlertDialog(
            title = stringResource(id = R.string.launch_jailbreak_heading),
            text = stringResource(id = R.string.launch_jailbreak_subheading),
            onDismissRequest = { showDialog = false },
            confirmButtonText =
                stringResource(id = R.string.common_ok)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            onClickConfirmButton = { showDialog = false },
            confirmButtonColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
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
