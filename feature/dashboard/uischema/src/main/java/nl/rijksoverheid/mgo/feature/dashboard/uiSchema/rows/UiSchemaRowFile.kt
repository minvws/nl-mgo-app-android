package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.notificationInformation
import nl.rijksoverheid.mgo.data.healthcare.binary.TEST_FHIR_BINARY
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.R
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
import nl.rijksoverheid.mgo.framework.util.shareFile
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a list item that represents a file that can be downloaded or opened.
 *
 * @param row The [UISchemaRow.File].
 * @param onClick Called when is requested to downloaded the file.
 * @param modifier The [Modifier] to be applied.
 */
@Composable
internal fun UiSchemaRowFile(
    row: UISchemaRow.File,
    onClick: (row: UISchemaRow.File.NotDownloaded) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    // Immediately share file when it is finished downloading
    LaunchedEffect(row) {
        if (row is UISchemaRow.File.Downloaded) {
            context.shareFile(file = row.binary.file, contentType = row.binary.contentType)
        }
    }

    when (row) {
        is UISchemaRow.File.NotDownloaded.Idle -> {
            UiSchemaRowFile(row = row, loading = false, modifier = modifier.clickable { onClick(row) })
        }

        is UISchemaRow.File.Loading -> {
            UiSchemaRowFile(row = row, loading = true, modifier = modifier)
        }

        is UISchemaRow.File.Downloaded -> {
            UiSchemaRowFile(
                row = row,
                loading = false,
                modifier =
                    modifier.clickable {
                        context.shareFile(file = row.binary.file, contentType = row.binary.contentType)
                    },
            )
        }
        is UISchemaRow.File.NotDownloaded.Error -> {
            UISchemaRowError(
                icon = R.drawable.ic_error,
                iconTint = MaterialTheme.colorScheme.notificationError(),
                heading = CopyR.string.hc_documents_error,
                onTryAgain = {
                    onClick(row)
                },
            )
        }

        is UISchemaRow.File.Empty -> {
            UISchemaRowError(
                icon = R.drawable.ic_info,
                iconTint = MaterialTheme.colorScheme.notificationInformation(),
                heading = CopyR.string.hc_documents_no_document,
                onTryAgain = null,
            )
        }
    }
}

@Composable
private fun UiSchemaRowFile(
    row: UISchemaRow,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
            color = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
            text = row.value,
            style = MaterialTheme.typography.bodySmall,
        )

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 3.dp,
                trackColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                color = MaterialTheme.colorScheme.backgroundTertiary(),
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.ic_attachment),
                tint = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun UISchemaRowError(
    @DrawableRes icon: Int,
    iconTint: Color,
    @StringRes heading: Int,
    onTryAgain: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(painter = painterResource(icon), tint = iconTint, contentDescription = null)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(heading),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
        )
        if (onTryAgain != null) {
            TextButton(onClick = onTryAgain) {
                Text(
                    text = stringResource(CopyR.string.common_try_again),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowFileIdlePreview() {
    MgoTheme {
        UiSchemaRowFile(
            row = UISchemaRow.File.NotDownloaded.Idle(heading = "Heading", value = "Value", binary = ""),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowFileLoadingPreview() {
    MgoTheme {
        UiSchemaRowFile(
            row = UISchemaRow.File.Loading(heading = "Heading", value = "Value"),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowFileDownloadedPreview() {
    MgoTheme {
        UiSchemaRowFile(
            row = UISchemaRow.File.Downloaded(heading = "Heading", value = "Value", binary = TEST_FHIR_BINARY),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowFileEmptyPreview() {
    MgoTheme {
        UiSchemaRowFile(
            row = UISchemaRow.File.Empty(heading = "Heading", value = "Value"),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowFileErrorPreview() {
    MgoTheme {
        UiSchemaRowFile(
            row = UISchemaRow.File.NotDownloaded.Error(heading = "Heading", value = "Value", binary = ""),
            onClick = {},
        )
    }
}
