package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollLazyColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.feature.localisation.organizationList.R
import nl.rijksoverheid.mgo.feature.localisation.organizationList.getCardState
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen to display health care providers. These health care providers were found automatically, meaning that
 * they were requested per data available.
 *
 * @param checkResults If all the checkboxes for the health care providers that are displayed checkboxes should be checked.
 * @param onNavigateBack Called when requested to navigate back.
 * @param onNavigateToDashboard Called when requested to navigate to the dashboard (root bottom bar screen).
 */
@Composable
fun OrganizationListAutomaticSearchScreen(
  checkResults: Boolean,
  onNavigateBack: (() -> Unit)?,
  onNavigateToDashboard: () -> Unit,
) {
  val viewModel: OrganizationListAutomaticScreenViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  LaunchedEffect(Unit) {
    viewModel.navigation.collectLatest {
      onNavigateToDashboard()
    }
  }
  OrganizationListAutomaticSearchScreenContent(
    viewState = viewState,
    onNavigateBack = onNavigateBack,
    onGetSearchResults = { viewModel.getSearchResults() },
    updateOrganization = { organization, added -> viewModel.updateOrganization(organization, added) },
    onUpdateOrganizations = { viewModel.updateOrganizations() },
  )
}

@Composable
private fun OrganizationListAutomaticSearchScreenContent(
  viewState: OrganizationListAutomaticScreenViewState,
  onNavigateBack: (() -> Unit)?,
  onUpdateOrganizations: () -> Unit,
  onGetSearchResults: () -> Unit,
  updateOrganization: (organization: MgoOrganization, added: Boolean) -> Unit,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)

  val primaryButton =
    when {
      viewState.error == null ->
        MgoBottomButton(
          text = stringResource(CopyR.string.common_to_overview),
          onClick = onUpdateOrganizations,
        )

      else ->
        MgoBottomButton(
          text =
            stringResource(
              id =
                CopyR.string
                  .common_try_again,
            ),
          onClick = onGetSearchResults,
        )
    }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    contentWindowInsets = WindowInsets.statusBars,
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(id = CopyR.string.organization_search_heading),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        MgoAutoScrollLazyColumn(
          modifier = Modifier.weight(1f),
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) { canScroll ->
          when {
            viewState.loading -> {
              item {
                LoadingContent(canScroll)
              }
            }

            viewState.error != null -> {
              item {
                ErrorContent()
              }
            }

            viewState.results.isEmpty() -> {
              // TODO: Empty state
            }

            else -> {
              item {
                Text(
                  modifier = Modifier.padding(bottom = 16.dp),
                  text = stringResource(id = CopyR.string.organisation_list_automatic_subheading),
                  style = MaterialTheme.typography.bodyMedium,
                )
              }

              items(viewState.results.size) { position ->
                ResultContent(
                  searchResult = viewState.results[position],
                  updateOrganization = updateOrganization,
                )
              }
            }
          }
        }

        MgoBottomButtons(
          primaryButton = primaryButton,
          isElevated = lazyListState.canScrollForward,
        )
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
        text = stringResource(id = CopyR.string.organization_search_searching),
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@Composable
private fun ResultContent(
  searchResult: MgoOrganization,
  updateOrganization: (organization: MgoOrganization, added: Boolean) -> Unit,
) {
  OrganizationListAutomaticCard(
    modifier =
      Modifier
        .padding(bottom = 8.dp),
    organization = searchResult,
    onCheckedChange = { checked ->
      updateOrganization(searchResult, checked)
    },
    cardState = searchResult.getCardState(),
  )
}

@Composable
private fun ColumnScope.ErrorContent() {
  Image(
    modifier =
      Modifier
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally),
    painter = painterResource(id = R.drawable.illustration_critical),
    contentDescription = null,
  )

  Text(
    modifier = Modifier.padding(top = 24.dp),
    text = stringResource(id = CopyR.string.common_error_subheading),
    style = MaterialTheme.typography.bodyMedium,
  )
}

@DefaultPreviews
@Composable
internal fun OrganizationListAutomaticSearchScreenLoadingPreview() {
  MgoTheme {
    OrganizationListAutomaticSearchScreenContent(
      viewState = OrganizationListAutomaticScreenViewState.initialState,
      onNavigateBack = {},
      onGetSearchResults = {},
      updateOrganization = { _, _ -> },
      onUpdateOrganizations = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationListAutomaticSearchScreenSearchResultsPreview() {
  MgoTheme {
    OrganizationListAutomaticSearchScreenContent(
      viewState =
        OrganizationListAutomaticScreenViewState.initialState.copy(
          loading = false,
          results =
            listOf(
              TEST_MGO_ORGANIZATION,
              TEST_MGO_ORGANIZATION,
              TEST_MGO_ORGANIZATION,
            ),
        ),
      onNavigateBack = {},
      onGetSearchResults = {},
      updateOrganization = { _, _ -> },
      onUpdateOrganizations = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationListAutomaticSearchScreenErrorPreview() {
  MgoTheme {
    OrganizationListAutomaticSearchScreenContent(
      viewState =
        OrganizationListAutomaticScreenViewState.initialState.copy(
          loading = false,
          error = IllegalStateException("Something went wrong"),
        ),
      onNavigateBack = {},
      onGetSearchResults = {},
      updateOrganization = { _, _ -> },
      onUpdateOrganizations = {},
    )
  }
}
