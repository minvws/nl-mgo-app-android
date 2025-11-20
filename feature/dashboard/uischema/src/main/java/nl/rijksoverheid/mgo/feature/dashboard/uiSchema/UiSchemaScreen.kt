package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRowStaticValue
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSection
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.pft.Pft
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowBinary
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowLink
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowReference
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows.UiSchemaRowStatic

object UiSchemaScreenTestTag {
  const val LIST = "UiSchemaScreenList"
  const val LIST_ITEM = "UiSchemaScreenListItem"
}

@Composable
fun UiSchemaScreen(
  organization: MgoOrganization,
  referenceId: MgoResourceReferenceId,
  isSummary: Boolean,
  isBottomSheet: Boolean = false,
  onNavigateBack: (() -> Unit)? = null,
  onNavigateToDetail: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
) {
  var uiSchemaBottomSheet: Pair<MgoOrganization, MgoResourceReferenceId>? by remember { mutableStateOf(null) }
  uiSchemaBottomSheet?.let { uiSchemaData ->
    UiSchemaBottomSheet(
      organization = uiSchemaData.first,
      referenceId = uiSchemaData.second,
      onDismissRequest = { uiSchemaBottomSheet = null },
    )
  }

  var pftBottomSheet: Pft? by remember { mutableStateOf(null) }
  pftBottomSheet?.let { pft ->
    PftBottomSheet(
      pft = pft,
      onDismissRequest = { pftBottomSheet = null },
    )
  }

  val viewModel =
    hiltViewModel<UiSchemaScreenViewModel, UiSchemaScreenViewModel.Factory>(
      creationCallback = { factory -> factory.create(organization = organization, referenceId = referenceId, isSummary = isSummary) },
    )
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.navigate.collectLatest { navigateToReferenceId ->
      if (navigateToReferenceId == referenceId || isBottomSheet) {
        // Called when clicked on "Bekijk alle X". Navigate to the detail page of this ui schema.
        onNavigateToDetail(organization, navigateToReferenceId)
      } else {
        // When navigating to a new ui schema, show it inside a bottom sheet.
        uiSchemaBottomSheet = Pair(organization, navigateToReferenceId)
      }
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
    onShowPft = { pft ->
      pftBottomSheet = pft
    },
    isBottomSheet = isBottomSheet,
    onNavigateBack = onNavigateBack,
  )
}

@Composable
private fun UiSchemaScreenContent(
  viewState: UiSchemaScreenViewState,
  isBottomSheet: Boolean,
  onClickReference: (row: UISchemaRow.Reference) -> Unit,
  onClickFile: (row: UISchemaRow.Binary.NotDownloaded) -> Unit,
  onShowPft: (pft: Pft) -> Unit,
  onNavigateBack: (() -> Unit)?,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)
  Scaffold(
    modifier =
      Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).then(
        if (isBottomSheet) Modifier.fillMaxHeight(0.95f) else Modifier,
      ),
    topBar = {
      MgoLargeTopAppBar(
        title = viewState.toolbarTitle,
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
        windowInsets = if (isBottomSheet) WindowInsets(0) else TopAppBarDefaults.windowInsets,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        LazyColumn(
          modifier = Modifier.testTag(UiSchemaScreenTestTag.LIST),
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) {
          items(viewState.sections.size) { position ->
            val section = viewState.sections[position]
            UiSchemaSection(
              section = section,
              onClickReference = onClickReference,
              onClickFile = onClickFile,
              onClickPft = onShowPft,
              modifier = Modifier.padding(bottom = 32.dp),
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
  onClickPft: (pft: Pft) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    val heading = section.heading
    if (heading != null) {
      Text(
        modifier = Modifier.padding(bottom = 12.dp),
        text = heading,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
      )
    } else {
      Spacer(modifier = Modifier.height(12.dp))
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
              UiSchemaRowStatic(modifier = Modifier.testTag(UiSchemaScreenTestTag.LIST_ITEM), row = row, onClickPft = onClickPft)
            }

            is UISchemaRow.Reference -> {
              UiSchemaRowReference(
                modifier = Modifier.testTag(UiSchemaScreenTestTag.LIST_ITEM),
                row = row,
                onClick = onClickReference,
              )
            }

            is UISchemaRow.Binary -> {
              UiSchemaRowBinary(
                modifier = Modifier.testTag(UiSchemaScreenTestTag.LIST_ITEM),
                row = row,
                onClick = onClickFile,
              )
            }

            is UISchemaRow.Link -> {
              UiSchemaRowLink(
                modifier = Modifier.testTag(UiSchemaScreenTestTag.LIST_ITEM),
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
                      value = listOf(UISchemaRowStaticValue("Row Value 1")),
                    ),
                    UISchemaRow.Static(
                      heading = "Row Heading 2",
                      value = listOf(UISchemaRowStaticValue("Row Value 2")),
                    ),
                  ),
              ),
              UISchemaSection(
                heading = "Section Heading 1",
                rows =
                  listOf(
                    UISchemaRow.Static(
                      heading = "Row Heading 3",
                      listOf(UISchemaRowStaticValue("Row Value 3")),
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
      isBottomSheet = false,
      onShowPft = {},
    )
  }
}
