package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

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
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaSection
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowFile
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowReference
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowStatic
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UiSchemaScreen(
    organization: MgoOrganization,
    mgoResource: MgoResource,
    isSummary: Boolean,
    onNavigateToUiSchema: (organization: MgoOrganization, mgoResource: MgoResource) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<UiSchemaScreenViewModel, UiSchemaScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(organization = organization, mgoResource = mgoResource, isSummary = isSummary) },
        )
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigate.collectLatest { mgoResource ->
            onNavigateToUiSchema(organization, mgoResource)
        }
    }

    UiSchemaScreenContent(
        viewState = viewState,
        onClickReference = { row ->
            viewModel.onClickReferenceRow(row)
        },
        onClickFile = { row ->
            viewModel.onClickFileRow(row)
        },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun UiSchemaScreenContent(
    viewState: UiSchemaScreenViewState,
    onClickReference: (row: UISchemaRow.Reference) -> Unit,
    onClickFile: (row: UISchemaRow.File.NotDownloaded) -> Unit,
    onNavigateBack: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = viewState.toolbarTitle,
        onNavigateBack = onNavigateBack,
        content = {
            LazyColumn {
                items(viewState.sections.size) { position ->
                    val section = viewState.sections[position]
                    UiSchemaSection(
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
private fun UiSchemaSection(
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
