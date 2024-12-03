package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.notificationInformation
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinary
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_ENTRY
import nl.rijksoverheid.mgo.data.uiSchema.UIEntry
import java.io.File
import java.lang.IllegalStateException
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun UiSchemaAttachmentListItem(
    entry: UIEntry,
    state: AttachmentState,
    onDownloadAttachment: (entry: UIEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    // When file is downloaded launch share intent without user interaction
    LaunchedEffect(state) {
        if (state is AttachmentState.Downloaded) {
            context.shareAttachment(file = state.binary.file, contentType = state.binary.contentType)
        }
    }
    when (state) {
        is AttachmentState.NotDownloaded ->
            UiSchemaAttachmentNotDownloadedListItem(
                modifier = modifier.clickable { onDownloadAttachment(entry) },
                entry = entry,
            )

        is AttachmentState.Loading -> UiSchemaAttachmentLoadingListItem()
        is AttachmentState.Downloaded ->
            UiSchemaAttachmentNotDownloadedListItem(
                modifier = modifier.clickable { context.shareAttachment(file = state.binary.file, contentType = state.binary.contentType) },
                entry = entry,
            )

        is AttachmentState.Empty ->
            UiSchemaAttachmentErrorListItem(
                icon = R.drawable.ic_info,
                iconTint = MaterialTheme.colorScheme.notificationInformation(),
                heading = CopyR.string.hc_documents_no_document,
            )
        is AttachmentState.Error ->
            UiSchemaAttachmentErrorListItem(
                icon = R.drawable.ic_error,
                iconTint = MaterialTheme.colorScheme.notificationError(),
                heading = CopyR.string.hc_documents_error,
            )
    }
}

@Composable
private fun UiSchemaAttachmentNotDownloadedListItem(
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

@Composable
private fun UiSchemaAttachmentErrorListItem(
    @DrawableRes icon: Int,
    iconTint: Color,
    @StringRes heading: Int,
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
internal fun UiSchemaAttachmentListItemNotDownloadedPreview() {
    MgoTheme {
        UiSchemaAttachmentListItem(
            entry = TEST_UI_ENTRY.copy(label = "file.pdf"),
            state = AttachmentState.NotDownloaded,
            onDownloadAttachment = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaAttachmentListItemDownloadedPreview() {
    MgoTheme {
        UiSchemaAttachmentListItem(
            entry = TEST_UI_ENTRY.copy(label = "file.pdf"),
            state = AttachmentState.Downloaded(binary = HealthCareBinary(file = File(""), contentType = "")),
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
            state = AttachmentState.Loading,
            onDownloadAttachment = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaAttachmentListItemEmptyPreview() {
    MgoTheme {
        UiSchemaAttachmentListItem(
            entry = TEST_UI_ENTRY.copy(label = "file.pdf"),
            state = AttachmentState.Empty,
            onDownloadAttachment = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun UiSchemaAttachmentListItemErrorPreview() {
    MgoTheme {
        UiSchemaAttachmentListItem(
            entry = TEST_UI_ENTRY.copy(label = "file.pdf"),
            state = AttachmentState.Error(IllegalStateException("Some error")),
            onDownloadAttachment = {},
        )
    }
}
