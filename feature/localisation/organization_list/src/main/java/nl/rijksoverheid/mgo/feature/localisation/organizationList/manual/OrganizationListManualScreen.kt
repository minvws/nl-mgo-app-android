package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollLazyColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoHtmlText
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.supportContacts
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.localisation.organizationList.R
import nl.rijksoverheid.mgo.feature.localisation.organizationList.getCardState
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object OrganizationListManualScreenTestTag {
  const val LIST = "OrganizationListManualScreenList"
}

/**
 * Composable that shows a screen to display health care providers. These health care providers are found when searching for them via a
 * name and city.
 *
 * @param name The name of the health care provider to search for.
 * @param city The city of the health care provider to search for.
 * @param onNavigateBack Called when requested to navigate back.
 * @param onNavigateToAddOrganization Called when requested to go back to the screen where you can search for health care providers.
 * @param onNavigateToDashboard Called when requested to navigate to the dashboard (root bottom bar screen).
 */
@Composable
fun OrganizationListManualScreen(
  name: String,
  city: String,
  onNavigateBack: () -> Unit,
  onNavigateToAddOrganization: () -> Unit,
  onNavigateToDashboard: () -> Unit,
) {
  val viewModel =
    hiltViewModel<OrganizationListManualScreenViewModel, OrganizationListManualScreenViewModel.Factory>(
      creationCallback = { factory -> factory.create(name, city) },
    )
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  LaunchedEffect(Unit) {
    viewModel.navigation.collectLatest {
      onNavigateToDashboard()
    }
  }
  OrganizationListManualScreenContent(
    viewState = viewState,
    onNavigateBack = onNavigateBack,
    onGetSearchResults = { viewModel.getSearchResults() },
    onAddSearchResult = { searchResult ->
      if (searchResult.added) {
        onNavigateToDashboard()
      } else {
        viewModel.addOrganization(searchResult)
      }
    },
    onNavigateToSearch = onNavigateToAddOrganization,
  )
}

@Composable
private fun OrganizationListManualScreenContent(
  viewState: OrganizationListManualScreenViewState,
  onNavigateBack: () -> Unit,
  onGetSearchResults: () -> Unit,
  onAddSearchResult: (provider: MgoOrganization) -> Unit,
  onNavigateToSearch: () -> Unit,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)
  val title =
    when {
      viewState.loading -> {
        stringResource(CopyR.string.organization_search_heading)
      }

      viewState.error != null -> {
        stringResource(CopyR.string.organization_search_no_results_found_heading)
      }

      viewState.results.isEmpty() -> {
        stringResource(CopyR.string.organization_search_no_results_found_heading)
      }

      else -> {
        stringResource(CopyR.string.organization_search_heading)
      }
    }

  val primaryButton =
    when {
      viewState.loading -> null
      viewState.error != null ->
        MgoBottomButton(
          text = stringResource(CopyR.string.common_try_again),
          onClick = onGetSearchResults,
        )

      viewState.results.isEmpty() ->
        MgoBottomButton(
          text = stringResource(CopyR.string.common_search_again),
          onClick = onNavigateToSearch,
        )

      else -> null
    }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    contentWindowInsets = if (primaryButton == null) ScaffoldDefaults.contentWindowInsets else WindowInsets.statusBars,
    topBar = {
      MgoLargeTopAppBar(
        title = title,
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        MgoAutoScrollLazyColumn(
          modifier = Modifier.weight(1f).testTag(OrganizationListManualScreenTestTag.LIST),
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
              item {
                EmptyContent(
                  name = viewState.name,
                  city = viewState.city,
                )
              }
            }

            else -> {
              items(viewState.results.size) { position ->
                ResultContent(
                  searchResult = viewState.results[position],
                  onAddSearchResult = onAddSearchResult,
                )
              }
            }
          }
        }

        if (primaryButton != null) {
          MgoBottomButtons(
            primaryButton = primaryButton,
            isElevated = lazyListState.canScrollForward,
          )
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
        text = stringResource(id = CopyR.string.organization_search_searching),
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@Composable
private fun ResultContent(
  searchResult: MgoOrganization,
  onAddSearchResult: (provider: MgoOrganization) -> Unit,
  modifier: Modifier = Modifier,
) {
  OrganizationListManualCard(
    modifier =
      modifier
        .padding(bottom = 8.dp),
    searchResult = searchResult,
    onClick = onAddSearchResult,
    cardState = searchResult.getCardState(),
  )
}

@Composable
private fun ColumnScope.EmptyContent(
  name: String,
  city: String,
) {
  Image(
    modifier =
      Modifier
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally),
    painter = painterResource(id = R.drawable.illustration_critical),
    contentDescription = null,
  )
  MgoHtmlText(
    modifier = Modifier.padding(top = 24.dp),
    html =
      stringResource(
        id = CopyR.string.organization_search_no_results_found_subheading,
        name,
        city,
      ),
    style = MaterialTheme.typography.bodyMedium,
  )

  EmptyListItem(
    modifier = Modifier.padding(top = 16.dp),
    text =
      stringResource(
        id = CopyR.string.organization_search_search_hint_1,
      ),
  )
  EmptyListItem(
    modifier = Modifier.padding(top = 8.dp),
    text =
      stringResource(
        id = CopyR.string.organization_search_search_hint_2,
      ),
  )
  EmptyListItem(
    modifier = Modifier.padding(top = 8.dp),
    text =
      stringResource(
        id = CopyR.string.organization_search_search_hint_3,
      ),
  )
}

@Composable
private fun EmptyListItem(
  text: String,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier, verticalAlignment = Alignment.Top) {
    Icon(
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null,
      tint = MaterialTheme.colorScheme.supportContacts(),
    )
    Text(modifier = Modifier.padding(start = 8.dp), text = text, style = MaterialTheme.typography.bodyMedium)
  }
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
internal fun OrganizationListManualScreenLoadingPreview() {
  MgoTheme {
    OrganizationListManualScreenContent(
      viewState = OrganizationListManualScreenViewState.initialState(name = "Tandarts Tandje Erbij", city = "Roermond"),
      onNavigateBack = {},
      onGetSearchResults = {},
      onAddSearchResult = {},
      onNavigateToSearch = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationListManualScreenEmptyPreview() {
  MgoTheme {
    OrganizationListManualScreenContent(
      viewState =
        OrganizationListManualScreenViewState
          .initialState(name = "Tandarts Tandje Erbij", city = "Roermond")
          .copy(
            loading = false,
          ),
      onNavigateBack = {},
      onGetSearchResults = {},
      onAddSearchResult = {},
      onNavigateToSearch = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationListManualScreenResultsPreview() {
  MgoTheme {
    OrganizationListManualScreenContent(
      viewState =
        OrganizationListManualScreenViewState
          .initialState(name = "Tandarts Tandje Erbij", city = "Roermond")
          .copy(
            loading = false,
            results = listOf(TEST_MGO_ORGANIZATION, TEST_MGO_ORGANIZATION, TEST_MGO_ORGANIZATION),
          ),
      onNavigateBack = {},
      onNavigateToSearch = {},
      onGetSearchResults = {},
      onAddSearchResult = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationListManualScreenErrorPreview() {
  MgoTheme {
    OrganizationListManualScreenContent(
      viewState =
        OrganizationListManualScreenViewState
          .initialState(name = "Tandarts Tandje Erbij", city = "Roermond")
          .copy(
            loading = false,
            error = IllegalStateException("Something went wrong"),
          ),
      onNavigateBack = {},
      onNavigateToSearch = {},
      onGetSearchResults = {},
      onAddSearchResult = {},
    )
  }
}
