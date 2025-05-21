package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.borderPrimary
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaSection
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowBinary
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowLink
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowReference
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowStatic

/**
 * Composable that shows a screen that displays health care data.
 * Health care data is showed via a list of [UISchemaRow].
 *
 * @param organization The [MgoOrganization] for the health care data.
 * @param mgoResource The [MgoResource] to get the health care data from.
 * @param isSummary If this screen shows a summary of the health care data, or the complete set.
 * @param onNavigateToUiSchema Called when navigating to another [UiSchemaScreen].
 * @param onNavigateBack Called when requested to navigate back.
 */
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
  onClickFile: (row: UISchemaRow.Binary.NotDownloaded) -> Unit,
  onNavigateBack: () -> Unit,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = viewState.toolbarTitle,
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        LazyColumn(
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) {
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
      }
    },
  )
}

@Composable
private fun UiSchemaSection(
  section: UISchemaSection,
  onClickReference: (row: UISchemaRow.Reference) -> Unit,
  onClickFile: (row: UISchemaRow.Binary.NotDownloaded) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    if (section.heading != null) {
      Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = section.heading,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
      )
    } else {
      Spacer(modifier = Modifier.height(8.dp))
    }

    MgoCard(
      modifier =
        Modifier
          .fillMaxWidth(),
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

            is UISchemaRow.Binary -> {
              UiSchemaRowBinary(
                row = row,
                onClick = onClickFile,
              )
            }

            is UISchemaRow.Link -> {
              UiSchemaRowLink(
                row = row,
              )
            }
          }
          if (index != section.rows.lastIndex) {
            HorizontalDivider(
              modifier =
                Modifier
                  .fillMaxWidth()
                  .padding(start = 16.dp),
              color = MaterialTheme.colorScheme.borderPrimary(),
              thickness = 0.33.dp,
            )
          }
        }
      }
    }
  }
}

@DefaultPreviews
@Composable
internal fun UiSchemaScreenContentPreview() {
  MgoTheme {
    UiSchemaScreenContent(
      viewState =
        UiSchemaScreenViewState(
          toolbarTitle = "Titel",
          sections =
            listOf(
              UISchemaSection(
                heading = null,
                rows =
                  listOf(
                    UISchemaRow.Static(
                      heading = "Row Heading 1",
                      value = "Row Value 1",
                    ),
                    UISchemaRow.Static(
                      heading = "Row Heading 2",
                      value = "Row Value 2",
                    ),
                  ),
              ),
              UISchemaSection(
                heading = "Section Heading 1",
                rows =
                  listOf(
                    UISchemaRow.Static(
                      heading = "Row Heading 3",
                      value = "Row Value 3",
                    ),
                  ),
              ),
              UISchemaSection(
                heading = "Section Heading 2",
                rows =
                  listOf(
                    UISchemaRow.Reference(
                      heading = null,
                      value = "Reference",
                      referenceId = "1",
                    ),
                  ),
              ),
              UISchemaSection(
                heading = "Section Heading 3",
                rows =
                  listOf(
                    UISchemaRow.Binary.NotDownloaded.Idle(
                      heading = null,
                      value = "File",
                      binary = "",
                    ),
                  ),
              ),
              UISchemaSection(
                heading = "Section Heading 4",
                rows =
                  listOf(
                    UISchemaRow.Link(
                      heading = null,
                      value = "Link",
                      url = "https://www.google.com",
                    ),
                  ),
              ),
            ),
        ),
      onClickReference = {},
      onClickFile = {},
      onNavigateBack = {},
    )
  }
}
