package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_ENTRY
import nl.rijksoverheid.mgo.data.uiSchema.UIEntry
import java.io.File
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun UiSchemaAttachmentListItem(
    entry: UIEntry,
    state: AttachmentState,
    onDownloadAttachment: (entry: UIEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when (state) {
        is AttachmentState.NotDownloaded ->
            UiSchemaAttachmentClickableListItem(
                modifier = modifier.clickable { onDownloadAttachment(entry) },
                entry = entry,
            )

        is AttachmentState.Loading -> UiSchemaAttachmentLoadingListItem()
        is AttachmentState.Downloaded ->
            UiSchemaAttachmentClickableListItem(
                modifier = modifier.clickable { context.shareAttachment(file = state.file, contentType = state.contentType) },
                entry = entry,
            )
    }
}

@Composable
private fun UiSchemaAttachmentClickableListItem(
    entry: UIEntry,
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
            text = entry.label.getStringFromResourceWithFallback(),
            style = MaterialTheme.typography.bodySmall,
        )
        Icon(
            painter = painterResource(R.drawable.ic_attachment),
            tint = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
            contentDescription = null,
        )
    }
}

@Composable
private fun UiSchemaAttachmentLoadingListItem(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 3.dp,
                color =
                    MaterialTheme.colorScheme
                        .iconsSecondary(),
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(CopyR.string.common_loading),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private fun Context.shareAttachment(
    file: File,
    contentType: String,
) {
    val attachmentUri: Uri =
        FileProvider.getUriForFile(
            this,
            "${this.packageName}.fileprovider",
            file,
        )

    val shareIntent =
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(attachmentUri, contentType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    this.startActivity(
        Intent.createChooser(shareIntent, "Open File"),
    )
}

@PreviewLightDark
@Composable
internal fun UiSchemaAttachmentListItemClickablePreview() {
    MgoTheme {
        UiSchemaAttachmentListItem(
            entry = TEST_UI_ENTRY.copy(label = "file.pdf"),
            state = AttachmentState.NotDownloaded(label = "file.pdf", url = "https://google.com"),
            onDownloadAttachment = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaAttachmentListItemLoadingPreview() {
    MgoTheme {
        UiSchemaAttachmentListItem(
            entry = TEST_UI_ENTRY.copy(label = "file.pdf"),
            state = AttachmentState.Loading(label = "file.pdf"),
            onDownloadAttachment = {},
        )
    }
}
