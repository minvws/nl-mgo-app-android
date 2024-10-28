package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoHtmlText
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.composable.debugerror.MgoDebugErrorButton
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.theme.R as ThemeR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun OrganizationSearchScreen(
    name: String,
    city: String,
    onNavigateBack: () -> Unit,
    onNavigateToAddOrganization: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val viewModel =
        hiltViewModel<OrganizationSearchScreenViewModel, OrganizationSearchScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(name, city) },
        )
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.navigation.collectLatest {
            onNavigateToDashboard()
        }
    }
    OrganizationSearchScreenContent(
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
private fun OrganizationSearchScreenContent(
    viewState: OrganizationSearchScreenViewState,
    onNavigateBack: () -> Unit,
    onGetSearchResults: () -> Unit,
    onAddSearchResult: (provider: MgoOrganization) -> Unit,
    onNavigateToSearch: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.organization_search_heading),
        onNavigateBack = onNavigateBack,
        content = {
            when {
                viewState.loading -> {
                    LoadingContent()
                }

                viewState.error != null -> {
                    ErrorContent(
                        error = viewState.error,
                        onButtonClick = onGetSearchResults,
                    )
                }

                viewState.results.isEmpty() -> {
                    EmptyContent(
                        name = viewState.name,
                        city = viewState.city,
                        onButtonClick = onNavigateToSearch,
                    )
                }

                else -> {
                    OrganizationSearchScreenContent(
                        searchResults = viewState.results,
                        onAddSearchResult = onAddSearchResult,
                    )
                }
            }
        },
    )
}

@Composable
private fun ColumnScope.LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .weight(1f),
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
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun OrganizationSearchScreenContent(
    searchResults: List<MgoOrganization>,
    onAddSearchResult: (provider: MgoOrganization) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(top = 2.dp)) {
        items(searchResults.size) { position ->
            OrganizationSearchCard(
                modifier =
                    Modifier
                        .padding(bottom = 8.dp)
                        .testTag(TEST_TAG_ORGANIZATION_SEARCH_CARD),
                searchResult = searchResults[position],
                onClick = onAddSearchResult,
            )
        }
    }
}

@Composable
private fun EmptyContent(
    name: String,
    city: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ColumnWithButtons(
        modifier = modifier,
        buttonText = stringResource(id = CopyR.string.common_search_again),
        onButtonClick = onButtonClick,
    ) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            painter = painterResource(id = ThemeR.drawable.illustration_alert),
            contentDescription = null,
        )
        MgoHtmlText(
            modifier = Modifier.padding(top = 24.dp),
            html = stringResource(id = CopyR.string.organization_search_no_results_found_subheading, name, city),
            style = MaterialTheme.typography.bodySmall,
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
            tint = MaterialTheme.colorScheme.supportHuisarts(),
        )
        Text(modifier = Modifier.padding(start = 8.dp), text = text, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ErrorContent(
    error: Throwable,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ColumnWithButtons(
        modifier = modifier,
        buttonText = stringResource(id = CopyR.string.common_try_again),
        onButtonClick = onButtonClick,
    ) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            painter = painterResource(id = ThemeR.drawable.illustration_alert),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = CopyR.string.common_error_subheading),
            style = MaterialTheme.typography.bodySmall,
        )

        MgoDebugErrorButton(error = error)
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationSearchScreenLoadingPreview() {
    MgoTheme {
        OrganizationSearchScreenContent(
            viewState = OrganizationSearchScreenViewState.initialState(name = "Tandarts Tandje Erbij", city = "Roermond"),
            onNavigateBack = {},
            onGetSearchResults = {},
            onAddSearchResult = {},
            onNavigateToSearch = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationSearchScreenEmptyPreview() {
    MgoTheme {
        OrganizationSearchScreenContent(
            viewState =
                OrganizationSearchScreenViewState.initialState(name = "Tandarts Tandje Erbij", city = "Roermond").copy(
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
internal fun OrganizationSearchScreenSearchResultsPreview() {
    MgoTheme {
        OrganizationSearchScreenContent(
            viewState =
                OrganizationSearchScreenViewState.initialState(name = "Tandarts Tandje Erbij", city = "Roermond").copy(
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
internal fun OrganizationSearchScreenErrorPreview() {
    MgoTheme {
        OrganizationSearchScreenContent(
            viewState =
                OrganizationSearchScreenViewState.initialState(name = "Tandarts Tandje Erbij", city = "Roermond").copy(
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
