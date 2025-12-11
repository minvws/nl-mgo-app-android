package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollLazyColumn
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.banner.MgoBanner
import nl.rijksoverheid.mgo.component.mgo.banner.MgoBannerType
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerBottomSheet
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_MEDICATION
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object HealthCategoryScreenTestTag {
  const val CARD = "HealthCategoryScreenCard"
}

@Composable
fun HealthCategoryScreen(
  category: HealthCategoryGroup.HealthCategory,
  onClickListItem: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
  onNavigateBack: () -> Unit,
  filterOrganization: MgoOrganization? = null,
) {
  val context = LocalContext.current
  val viewModel =
    hiltViewModel<HealthCategoryScreenViewModel, HealthCategoryScreenViewModel.Factory>(
      creationCallback = { factory -> factory.create(category = category, filterOrganization = filterOrganization) },
    )
  val viewState by viewModel.viewState.collectAsState()

  var pdfViewerState: PdfViewerState? by remember { mutableStateOf(null) }
  pdfViewerState?.let { state ->
    PdfViewerBottomSheet(
      appBarTitle = LocalContext.current.getString(category.heading),
      state = state,
      onDismissRequest = {
        pdfViewerState = null
      },
    )
  }

  LaunchedEffect(Unit) {
    viewModel.openPdfViewer.collectLatest { state ->
      pdfViewerState = state
    }
  }

  var showExportPdfDialog by remember { mutableStateOf(false) }
  if (showExportPdfDialog) {
    MgoAlertDialog(
      heading = stringResource(CopyR.string.export_pdf_dialog_heading, LocalContext.current.getString(category.heading).lowercase()),
      subHeading = stringResource(CopyR.string.export_pdf_dialog_subheading),
      positiveButtonText = stringResource(CopyR.string.export_pdf_dialog_create_document),
      positiveButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
      negativeButtonText = stringResource(CopyR.string.common_cancel),
      negativeButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
      onClickPositiveButton = {
        showExportPdfDialog = false
        viewModel.generatePdf()
      },
      onClickNegativeButton = {
        showExportPdfDialog = false
      },
      onDismissRequest = {
        showExportPdfDialog = false
      },
    )
  }

  HealthCategoryScreenContent(
    viewState = viewState,
    onClickListItem = { organization, referenceId ->
      onClickListItem(organization, referenceId)
    },
    onRetry = { viewModel.retry() },
    onGeneratePdf = {
      showExportPdfDialog = true
    },
    onNavigateBack = onNavigateBack,
  )
}

@Composable
private fun HealthCategoryScreenContent(
  viewState: HealthCategoryScreenViewState,
  onRetry: () -> Unit,
  onClickListItem: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
  onGeneratePdf: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)
  var showErrorBanner by remember(viewState.showErrorBanner) { mutableStateOf(viewState.showErrorBanner) }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = LocalContext.current.getString(viewState.category.heading),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
        actions = {
          if (viewState.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded) {
            IconButton(onGeneratePdf) {
              Icon(Icons.Outlined.PictureAsPdf, null)
            }
          }
        },
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        MgoAutoScrollLazyColumn(
          modifier = Modifier.weight(1f),
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) { canScroll ->

          when (viewState.listItemsState) {
            HealthCategoryScreenViewState.ListItemsState.Loading -> {
              item {
                LoadingContent(canScroll)
              }
            }

            HealthCategoryScreenViewState.ListItemsState.NoData -> {
              item {
                NoDataContent(
                  canScroll = canScroll,
                  showErrorBanner = showErrorBanner,
                  onRetryClick = onRetry,
                  onDismissErrorBanner = { showErrorBanner = false },
                )
              }
            }

            is HealthCategoryScreenViewState.ListItemsState.Loaded -> {
              LoadedContent(
                listItemsGroup = viewState.listItemsState.listItemsGroup,
                onClickListItem = onClickListItem,
                showErrorBanner = showErrorBanner,
                onRetryClick = onRetry,
                onDismissErrorBanner = { showErrorBanner = false },
              )
            }
          }
        }
      }
    },
  )
}

@Composable
private fun LazyItemScope.LoadingContent(canScroll: Boolean) {
  Box(
    modifier = if (canScroll) Modifier else Modifier.fillParentMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      CircularProgressIndicator(
        modifier = Modifier.size(48.dp),
        strokeWidth = 6.dp,
      )
      Text(
        modifier = Modifier.padding(top = 20.dp),
        text = stringResource(id = CopyR.string.common_loading),
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@Suppress("ktlint:standard:function-naming")
private fun LazyListScope.LoadedContent(
  listItemsGroup: List<HealthCategoryScreenListItemsGroup>,
  onClickListItem: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
  showErrorBanner: Boolean,
  onRetryClick: () -> Unit,
  onDismissErrorBanner: () -> Unit,
) {
  if (showErrorBanner) {
    item {
      MgoBanner(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        type = MgoBannerType.WARNING,
        heading = stringResource(id = CopyR.string.common_error_heading),
        subHeading = stringResource(id = CopyR.string.common_error_subheading),
        buttonText = stringResource(id = CopyR.string.common_try_again),
        onButtonClick = onRetryClick,
        onDismiss = onDismissErrorBanner,
      )
    }
  }

  for (listItemGroup in listItemsGroup) {
    item {
      Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = listItemGroup.heading,
        style = MaterialTheme.typography.headlineMedium,
      )
    }
    for (listItem in listItemGroup.items) {
      item {
        HealthCategoryCard(
          modifier =
            Modifier
              .fillMaxWidth()
              .padding(bottom = 16.dp),
          title = listItem.title,
          subtitle = listItem.subtitle,
          onClick = { onClickListItem(listItem.organization, listItem.mgoResource.referenceId) },
        )
      }
    }
  }
}

@Composable
private fun LazyItemScope.NoDataContent(
  canScroll: Boolean,
  showErrorBanner: Boolean,
  onRetryClick: () -> Unit,
  onDismissErrorBanner: () -> Unit,
) {
  Column(
    modifier = if (canScroll) Modifier else Modifier.fillParentMaxSize(),
  ) {
    if (showErrorBanner) {
      MgoBanner(
        modifier =
          Modifier
            .fillMaxWidth(),
        type = MgoBannerType.WARNING,
        heading = stringResource(id = CopyR.string.common_error_heading),
        subHeading = stringResource(id = CopyR.string.common_error_subheading),
        buttonText = stringResource(id = CopyR.string.common_try_again),
        onButtonClick = onRetryClick,
        onDismiss = onDismissErrorBanner,
      )
    }

    Column(
      modifier =
        Modifier
          .weight(1f)
          .padding(top = 16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        modifier =
          Modifier
            .fillMaxWidth()
            .height(156.dp),
        painter = painterResource(id = R.drawable.illustration_health_category_empty),
        contentDescription = null,
      )
      Text(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        text = stringResource(id = CopyR.string.health_category_empty_heading),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
      )
      Text(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        text = stringResource(id = CopyR.string.health_category_empty_subheading),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
        textAlign = TextAlign.Center,
      )
    }
  }
}

@Composable
private fun HealthCategoryCard(
  title: String,
  subtitle: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier.testTag(HealthCategoryScreenTestTag.CARD), onClick = onClick) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
      )
      Text(
        modifier = Modifier.padding(top = 8.dp),
        text = subtitle,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
      )
    }
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenLoadingPreview() {
  MgoTheme {
    HealthCategoryScreenContent(
      viewState =
        HealthCategoryScreenViewState(
          category = TEST_HEALTH_CATEGORY_PROBLEMS,
          listItemsState = HealthCategoryScreenViewState.ListItemsState.Loading,
          showErrorBanner = false,
        ),
      onClickListItem = { _, _ -> },
      onRetry = {},
      onGeneratePdf = {},
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenLoadedPreview() {
  MgoTheme {
    HealthCategoryScreenContent(
      viewState =
        HealthCategoryScreenViewState(
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          listItemsState =
            HealthCategoryScreenViewState.ListItemsState.Loaded(
              listItemsGroup = listOf(TEST_LIST_ITEM_GROUP),
            ),
          showErrorBanner = false,
        ),
      onClickListItem = { _, _ -> },
      onRetry = {},
      onGeneratePdf = {},
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenListItemsWithErrorPreview() {
  MgoTheme {
    HealthCategoryScreenContent(
      viewState =
        HealthCategoryScreenViewState(
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          listItemsState =
            HealthCategoryScreenViewState.ListItemsState.Loaded(
              listItemsGroup = listOf(TEST_LIST_ITEM_GROUP),
            ),
          showErrorBanner = true,
        ),
      onClickListItem = { _, _ -> },
      onRetry = {},
      onGeneratePdf = {},
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenNoDataPreview() {
  MgoTheme {
    MgoTheme {
      HealthCategoryScreenContent(
        viewState =
          HealthCategoryScreenViewState(
            category = TEST_HEALTH_CATEGORY_MEDICATION,
            listItemsState = HealthCategoryScreenViewState.ListItemsState.NoData,
            showErrorBanner = false,
          ),
        onClickListItem = { _, _ -> },
        onRetry = {},
        onGeneratePdf = {},
        onNavigateBack = {},
      )
    }
  }
}
