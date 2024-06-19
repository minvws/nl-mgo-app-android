package nl.rijksoverheid.mgo.feature.localisation.searchresults

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.composable.MgoSpannableText
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.theme.R as ThemeR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SearchResultsScreen(
    name: String,
    city: String,
    onNavigateBack: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val viewModel: SearchResultsScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getSearchResults(name = name, city = city)
        viewModel.navigation.collectLatest {
            onNavigateToDashboard()
        }
    }
    HealthCareSearchResultsScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onGetSearchResults = { viewModel.getSearchResults(name = name, city = city) },
        onAddSearchResult = { searchResult ->
            if (searchResult.added) {
                onNavigateToDashboard()
            } else {
                viewModel.addHealthCareProvider(searchResult)
            }
        },
        onNavigateToSearch = onNavigateToSearch,
    )
}

@Composable
private fun HealthCareSearchResultsScreenContent(
    viewState: SearchResultsScreenViewState,
    onNavigateBack: () -> Unit,
    onGetSearchResults: () -> Unit,
    onAddSearchResult: (provider: HealthCareProvider) -> Unit,
    onNavigateToSearch: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.general_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            when (viewState) {
                SearchResultsScreenViewState.Loading -> {
                    LoadingContent(modifier = Modifier.padding(innerPadding))
                }

                is SearchResultsScreenViewState.Success -> {
                    if (viewState.results.isEmpty()) {
                        EmptyContent(
                            modifier = Modifier.padding(innerPadding),
                            name = viewState.name,
                            city = viewState.city,
                            onButtonClick = onNavigateToSearch,
                        )
                    } else {
                        SearchResultsContent(
                            modifier = Modifier.padding(innerPadding),
                            searchResults = viewState.results,
                            onAddSearchResult = onAddSearchResult,
                        )
                    }
                }

                is SearchResultsScreenViewState.Error ->
                    ErrorContent(
                        isProductionBuild = viewState.isProductionBuild,
                        error = viewState.error,
                        onButtonClick = onGetSearchResults,
                    )
            }
        },
    )
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = CopyR.string.localisation_searchresults_title),
            style = MaterialTheme.typography.headingLarge,
            fontWeight = FontWeight.Bold,
        )

        CircularProgressIndicator(
            modifier =
                Modifier
                    .padding(top = 72.dp)
                    .size(48.dp),
            strokeWidth = 6.dp,
        )
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(id = CopyR.string.localisation_searchresults_loading),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun SearchResultsContent(
    searchResults: List<HealthCareProvider>,
    onAddSearchResult: (provider: HealthCareProvider) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(horizontal = 16.dp)) {
        item {
            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(id = CopyR.string.localisation_searchresults_title),
                style = MaterialTheme.typography.headingLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        items(searchResults.size) { position ->
            SearchResultCard(
                modifier =
                    Modifier
                        .padding(bottom = 8.dp)
                        .testTag(TEST_TAG_SEARCH_RESULT_CARD),
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
        buttonText = stringResource(id = CopyR.string.general_search_again),
        onButtonClick = onButtonClick,
    ) {
        Text(
            text = stringResource(id = CopyR.string.localisation_searchresults_empty_title),
            style = MaterialTheme.typography.headingLarge,
            fontWeight = FontWeight.Bold,
        )

        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp),
            painter = painterResource(id = ThemeR.drawable.illustration_alert),
            contentDescription = null,
        )
        MgoSpannableText(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = CopyR.string.localisation_searchresults_empty_list_header, name, city),
            style = MaterialTheme.typography.bodySmall,
        )

        EmptyListItem(
            modifier = Modifier.padding(top = 16.dp),
            text =
                stringResource(
                    id = CopyR.string.localisation_searchresults_empty_list_item_1,
                ),
        )
        EmptyListItem(
            modifier = Modifier.padding(top = 8.dp),
            text =
                stringResource(
                    id = CopyR.string.localisation_searchresults_empty_list_item_2,
                ),
        )
        EmptyListItem(
            modifier = Modifier.padding(top = 8.dp),
            text =
                stringResource(
                    id = CopyR.string.localisation_searchresults_empty_list_item_3,
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
            tint = MaterialTheme.colors.supportHuisarts(),
        )
        MgoSpannableText(modifier = Modifier.padding(start = 8.dp), text = text, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ErrorContent(
    isProductionBuild: Boolean,
    error: Throwable,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ColumnWithButtons(
        modifier = modifier,
        buttonText = stringResource(id = CopyR.string.general_try_again),
        onButtonClick = onButtonClick,
    ) {
        Text(
            text = stringResource(id = CopyR.string.error_title),
            style = MaterialTheme.typography.headingLarge,
            fontWeight = FontWeight.Bold,
        )

        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp),
            painter = painterResource(id = ThemeR.drawable.illustration_alert),
            contentDescription = null,
        )

        MgoSpannableText(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = CopyR.string.error_subtitle),
            style = MaterialTheme.typography.bodySmall,
        )

        if (!isProductionBuild) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = error.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun HealthCareSearchResultsLoadingPreview() {
    MgoTheme {
        HealthCareSearchResultsScreenContent(
            viewState = SearchResultsScreenViewState.Loading,
            onNavigateBack = {},
            onGetSearchResults = {},
            onAddSearchResult = {},
            onNavigateToSearch = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun HealthCareSearchResultsEmptyPreview() {
    MgoTheme {
        HealthCareSearchResultsScreenContent(
            viewState =
                SearchResultsScreenViewState.Success(
                    name = "Tandarts Tandje Erbij",
                    city = "Roermond",
                    results = listOf(),
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
internal fun HealthCareSearchResultsPreview() {
    MgoTheme {
        HealthCareSearchResultsScreenContent(
            viewState =
                SearchResultsScreenViewState.Success(
                    name = "Tandarts Tandje Erbij",
                    city = "Roermond",
                    results =
                        listOf(
                            TEST_HEALTH_CARE_PROVIDER,
                            TEST_HEALTH_CARE_PROVIDER,
                            TEST_HEALTH_CARE_PROVIDER,
                        ),
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
internal fun HealthCareSearchResultsErrorPreview() {
    MgoTheme {
        HealthCareSearchResultsScreenContent(
            viewState =
                SearchResultsScreenViewState.Error(
                    isProductionBuild = false,
                    error = IllegalStateException("Something went wrong"),
                ),
            onNavigateBack = {},
            onNavigateToSearch = {},
            onGetSearchResults = {},
            onAddSearchResult = {},
        )
    }
}
