package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import getString
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.error.ErrorBanner
import nl.rijksoverheid.mgo.component.error.ErrorBannerLoading
import nl.rijksoverheid.mgo.component.error.ErrorBannerState
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollLazyColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerBottomSheet
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
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
  val viewModel =
    hiltViewModel<HealthCategoryScreenViewModel, HealthCategoryScreenViewModel.Factory>(
      creationCallback = { factory -> factory.create(category = category, filterOrganization = filterOrganization) },
    )
  val viewState by viewModel.viewState.collectAsState()

  var pdfViewerState: PdfViewerState? by remember { mutableStateOf(null) }
  pdfViewerState?.let { state ->
    PdfViewerBottomSheet(
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
              Icon(painter = painterResource(R.drawable.ic_download), null)
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

          when (val listItemState = viewState.listItemsState) {
            HealthCategoryScreenViewState.ListItemsState.Loading -> {
              item {
                LoadingContent(canScroll)
              }
            }

            HealthCategoryScreenViewState.ListItemsState.NoData -> {
              item {
                EmptyContent(
                  icon = R.drawable.ic_category_empty,
                  heading = CopyR.string.health_category_empty_heading,
                  subheading = CopyR.string.health_category_empty_subheading,
                  buttonText = CopyR.string.health_category_empty_action,
                  onClickButton = onNavigateBack,
                  canScroll = canScroll,
                )
              }
            }

            is HealthCategoryScreenViewState.ListItemsState.Error -> {
              item {
                EmptyContent(
                  icon = R.drawable.ic_category_error,
                  heading = CopyR.string.health_category_errornodata_heading,
                  subheading =
                    when (listItemState) {
                      HealthCategoryScreenViewState.ListItemsState.Error.ServerError -> CopyR.string.errorstate_serverside_heading
                      HealthCategoryScreenViewState.ListItemsState.Error.UserError -> CopyR.string.errorstate_clientside_heading
                    },
                  buttonText = CopyR.string.common_try_again,
                  onClickButton = onRetry,
                  canScroll = canScroll,
                )
              }
            }

            is HealthCategoryScreenViewState.ListItemsState.Loaded -> {
              LoadedContent(
                listItemsGroup = listItemState.listItemsGroup,
                onClickListItem = onClickListItem,
                banner = viewState.banner,
                onRetryClick = onRetry,
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
        modifier =
          Modifier
            .size(40.dp),
        strokeWidth = 4.dp,
        trackColor = MaterialTheme.colorScheme.CategoriesRijkslint().copy(alpha = 0.15f),
        color = MaterialTheme.colorScheme.CategoriesRijkslint(),
      )
      Text(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(id = CopyR.string.errorstate_loading),
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@Suppress("ktlint:standard:function-naming")
private fun LazyListScope.LoadedContent(
  listItemsGroup: List<HealthCategoryScreenListItemsGroup>,
  onClickListItem: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
  banner: ErrorBannerState?,
  onRetryClick: () -> Unit,
) {
  item(key = banner.hashCode()) {
    when (banner) {
      ErrorBannerState.Loading -> {
        ErrorBannerLoading(
          modifier = Modifier.padding(bottom = 32.dp).animateItem(),
        )
      }

      is ErrorBannerState.Error.ServerError -> {
        ErrorBanner(
          modifier = Modifier.padding(bottom = 32.dp).animateItem(),
          state =
            ErrorBannerState.Error
              .ServerError(banner.partial),
          onClickRetry = onRetryClick,
        )
      }

      is ErrorBannerState.Error.UserError -> {
        ErrorBanner(
          modifier = Modifier.padding(bottom = 32.dp).animateItem(),
          state =
            ErrorBannerState.Error
              .ServerError(banner.partial),
          onClickRetry = onRetryClick,
        )
      }

      null -> {
      }
    }
  }

  for (listItemGroup in listItemsGroup) {
    if (listItemGroup.items.isNotEmpty()) {
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
            detail = listItem.detail,
            onClick = { onClickListItem(listItem.organization, listItem.mgoResource.referenceId) },
          )
        }
      }
    }
  }
}

@Composable
private fun LazyItemScope.EmptyContent(
  @DrawableRes icon: Int,
  @StringRes heading: Int,
  @StringRes subheading: Int,
  @StringRes buttonText: Int,
  onClickButton: () -> Unit,
  canScroll: Boolean,
) {
  Column(
    modifier = if (canScroll) Modifier.padding(top = 16.dp) else Modifier.fillParentMaxSize().padding(top = 16.dp),
  ) {
    Column(
      modifier = if (canScroll) Modifier else Modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        modifier =
          Modifier
            .fillMaxWidth(),
        painter = painterResource(id = icon),
        contentDescription = null,
      )

      Text(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        text = stringResource(id = heading),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
      )

      Text(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 16.dp),
        text = stringResource(id = subheading),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
        textAlign = TextAlign.Center,
      )
    }

    MgoBottomButtons(
      primaryButton =
        MgoBottomButton(
          text = stringResource(id = buttonText),
          onClick = onClickButton,
        ),
      isElevated = false,
      horizontalPadding = 0.dp,
    )
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
          banner = null,
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
          banner = null,
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
            banner = null,
          ),
        onClickListItem = { _, _ -> },
        onRetry = {},
        onGeneratePdf = {},
        onNavigateBack = {},
      )
    }
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenUserErrorPreview() {
  MgoTheme {
    MgoTheme {
      HealthCategoryScreenContent(
        viewState =
          HealthCategoryScreenViewState(
            category = TEST_HEALTH_CATEGORY_MEDICATION,
            listItemsState = HealthCategoryScreenViewState.ListItemsState.Error.UserError,
            banner = null,
          ),
        onClickListItem = { _, _ -> },
        onRetry = {},
        onGeneratePdf = {},
        onNavigateBack = {},
      )
    }
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenServerErrorPreview() {
  MgoTheme {
    MgoTheme {
      HealthCategoryScreenContent(
        viewState =
          HealthCategoryScreenViewState(
            category = TEST_HEALTH_CATEGORY_MEDICATION,
            listItemsState = HealthCategoryScreenViewState.ListItemsState.Error.ServerError,
            banner = null,
          ),
        onClickListItem = { _, _ -> },
        onRetry = {},
        onGeneratePdf = {},
        onNavigateBack = {},
      )
    }
  }
}
