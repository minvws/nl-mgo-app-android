package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.DisplayElement
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UIEntry
import nl.rijksoverheid.mgo.data.uiSchema.UIEntryDisplay
import nl.rijksoverheid.mgo.data.uiSchema.UIEntryType
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UISchemaGroup
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun UiSchemaDetailScreen(
    toolbarTitle: String,
    organization: MgoOrganization,
    uiSchema: UISchema,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<UiSchemaDetailScreenViewModel, UiSchemaDetailScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(organization = organization, uiSchema = uiSchema) },
        )
    val attachmentsState by viewModel.attachmentsState.collectAsStateWithLifecycle()
    UiSchemaDetailScreenContent(
        toolbarTitle = toolbarTitle,
        uiSchema = uiSchema,
        attachmentsState = attachmentsState,
        onDownloadAttachment = { entry ->
            viewModel.onDownloadAttachment(entry)
        },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun UiSchemaDetailScreenContent(
    toolbarTitle: String,
    uiSchema: UISchema,
    attachmentsState: Map<UIEntry, AttachmentState>,
    onDownloadAttachment: (entry: UIEntry) -> Unit,
    onNavigateBack: () -> Unit,
) {
    nl.rijksoverheid.mgo.component.mgo.MgoScaffold(
        appBarTitle = toolbarTitle,
        onNavigateBack = onNavigateBack,
        content = {
            LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                items(uiSchema.children.size) { position ->
                    val uiSchemaGroup = uiSchema.children[position]
                    UiSchemaSection(
                        modifier = Modifier.padding(bottom = 24.dp),
                        group = uiSchemaGroup,
                        attachmentsState = attachmentsState,
                        onDownloadAttachment = onDownloadAttachment,
                    )
                }
            }
        },
    )
}

@Composable
private fun UiSchemaSection(
    group: UISchemaGroup,
    attachmentsState: Map<UIEntry, AttachmentState>,
    onDownloadAttachment: (entry: UIEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = group.label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
        )
        nl.rijksoverheid.mgo.component.mgo.MgoCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
        ) {
            Column {
                group.children.forEachIndexed { index, entry ->
                    when (entry.type) {
                        UIEntryType.DownloadLink -> {
                            val attachmentState = attachmentsState[entry]
                            if (attachmentState != null) {
                                UiSchemaAttachmentListItem(
                                    entry = entry,
                                    state = attachmentState,
                                    onDownloadAttachment = onDownloadAttachment,
                                )
                            }
                        }

                        else -> {
                            UiSchemaLabelWithValueListItem(
                                entry = entry,
                                hasDivider = index != group.children.lastIndex,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UiSchemaLabelWithValueListItem(
    entry: UIEntry,
    hasDivider: Boolean,
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = entry.label,
            style = MaterialTheme.typography.bodySmallMini,
            color = MaterialTheme.colorScheme.contentTertiary(),
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
            text = entry.display.getStringOrUnknown(),
            style = MaterialTheme.typography.bodySmall,
        )
        if (hasDivider) {
            HorizontalDivider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                color = MaterialTheme.colorScheme.strokesPrimary(),
                thickness = 0.33.dp,
            )
        }
    }
}

@Composable
private fun UIEntryDisplay?.getStringOrUnknown(): String {
    return when (this) {
        is UIEntryDisplay.StringValue -> this.value
        is UIEntryDisplay.UnionArrayValue -> this.value.joinToString(", ") { it.getString() }
        else -> ""
    }
}

private fun DisplayElement.getString(): String {
    return when (this) {
        is DisplayElement.StringValue -> this.value
        is DisplayElement.StringArrayValue -> this.value.joinToString(", ")
    }
}

@DefaultPreviews
@Composable
internal fun UiSchemaDetailScreenPreview() {
    MgoTheme {
        UiSchemaDetailScreenContent(
            toolbarTitle = stringResource(id = CopyR.string.hc_medication_heading_detail),
            uiSchema = TEST_UI_SCHEMA_MEDICATION,
            attachmentsState = mapOf(),
            onDownloadAttachment = {},
            onNavigateBack = {},
        )
    }
}
