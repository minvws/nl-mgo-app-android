package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.shared.DisplayElement
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElement
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElementDisplay
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElementType
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchemaGroup
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.models.UISchemaRow
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.models.UISchemaSection
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.rows.UiSchemaRowFile
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.rows.UiSchemaRowReference
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.rows.UiSchemaRowStatic
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UiSchemaDetailScreen(
    organization: MgoOrganization,
    mgoResource: MgoResource,
    isSummary: Boolean,
    onNavigateToUiSchema: (organization: MgoOrganization, mgoResource: MgoResource) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<UiSchemaDetailScreenViewModel, UiSchemaDetailScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(organization = organization, mgoResource = mgoResource, isSummary = isSummary) },
        )
    val sections by viewModel.sections.collectAsStateWithLifecycle()
    val uiSchema by viewModel.uiSchema.collectAsStateWithLifecycle()
    val attachmentsState by viewModel.attachmentsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigate.collectLatest { mgoResource ->
            onNavigateToUiSchema(organization, mgoResource)
        }
    }

    uiSchema?.let {
        UiSchemaDetailScreenContent(
            toolbarTitle = it.label ?: "",
            uiSchema = it,
            sections = sections,
            attachmentsState = attachmentsState,
            onClickReference = { row ->
                viewModel.onClickReferenceRow(row)
            },
            onClickFile = { row ->
                viewModel.onClickFileRow(row)
            },
            onDownloadAttachment = { entry ->
                viewModel.onDownloadAttachment(entry)
            },
            onNavigateBack = onNavigateBack,
        )
    }
}

@Composable
private fun UiSchemaDetailScreenContent(
    toolbarTitle: String,
    uiSchema: UISchema,
    sections: List<UISchemaSection>,
    attachmentsState: Map<UIElement, AttachmentState>,
    onClickReference: (row: UISchemaRow.Reference) -> Unit,
    onClickFile: (row: UISchemaRow.File.NotDownloaded) -> Unit,
    onDownloadAttachment: (entry: UIElement) -> Unit,
    onNavigateBack: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = toolbarTitle,
        onNavigateBack = onNavigateBack,
        content = {
            LazyColumn {
                items(sections.size) { position ->
                    val section = sections[position]
                    NewUiSchemaSection(
                        section = section,
                        onClickReference = onClickReference,
                        onClickFile = onClickFile,
                        modifier = Modifier.padding(bottom = 24.dp),
                    )
                }
            }
        },
    )
}

@Composable
private fun NewUiSchemaSection(
    section: UISchemaSection,
    onClickReference: (row: UISchemaRow.Reference) -> Unit,
    onClickFile: (row: UISchemaRow.File.NotDownloaded) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        section.heading?.let {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = it,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
            )
        }
        MgoCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
        ) {
            Column {
                section.rows.forEachIndexed { index, row ->
                    when (row) {
                        is UISchemaRow.Static -> {
                            UiSchemaRowStatic(row = row)
                        }
                        is UISchemaRow.Reference -> {
                            UiSchemaRowReference(
                                row = row,
                                onClick = onClickReference,
                            )
                        }
                        is UISchemaRow.File -> {
                            UiSchemaRowFile(
                                row = row,
                                onClick = onClickFile,
                            )
                        }
                    }
                    if (index != section.rows.lastIndex) {
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
        }
    }
}

@Composable
private fun UiSchemaSection(
    group: UISchemaGroup,
    attachmentsState: Map<UIElement, AttachmentState>,
    onClickReference: (referenceId: String) -> Unit,
    onDownloadAttachment: (entry: UIElement) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        group.label?.let {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = it,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
            )
        }
        MgoCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
        ) {
            Column {
                group.children.forEachIndexed { index, entry ->
                    when (entry.type) {
                        UIElementType.ReferenceLink -> {
                            UiSchemaReference(
                                entry = entry,
                                onClick = onClickReference,
                            )
                        }

                        UIElementType.DownloadLink -> {
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
private fun UiSchemaReference(
    entry: UIElement,
    onClick: (referenceId: String) -> Unit,
) {
    Text(
        modifier =
            Modifier
                .padding(16.dp)
                .clickable { onClick(entry.reference ?: "") },
        text = entry.label,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
private fun UiSchemaLabelWithValueListItem(
    entry: UIElement,
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
private fun UIElementDisplay?.getStringOrUnknown(): String {
    return when (this) {
        is UIElementDisplay.StringValue -> this.value
        is UIElementDisplay.UnionArrayValue -> this.value.joinToString(", ") { it.getString() }
        else -> ""
    }
}

private fun DisplayElement.getString(): String {
    return when (this) {
        is DisplayElement.StringValue -> this.value
        is DisplayElement.StringArrayValue -> this.value.joinToString(", ")
    }
}
