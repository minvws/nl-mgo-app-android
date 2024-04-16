package nl.rijksoverheid.mgo.feature.localisation.getsearchresults

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.data.localisation.models.SearchResult
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SearchResultsScreen() {
    val viewModel: SearchResultsScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    SearchResultsScreenContent(viewState = viewState)
}

@Composable
private fun SearchResultsScreenContent(viewState: SearchResultsViewState) {
    val navigationManager = LocalNavigationManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navigationManager.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.general_previous),
                        )
                    }
                },
            )
        },
        backgroundColor = Color.Transparent,
        content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding), contentPadding = PaddingValues(horizontal = 16.dp)) {
                item {
                    Text(
                        text = stringResource(id = R.string.localisation_search_title),
                        style = MaterialTheme.typography.headingLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
                when (viewState) {
                    SearchResultsViewState.Loading -> {
                        loadingContent()
                    }

                    is SearchResultsViewState.Success -> {
                        searchResultsContent(viewState.results)
                    }

                    is SearchResultsViewState.Error -> {
                        errorContent(viewState.error)
                    }
                }
            }
        },
    )
}

private fun LazyListScope.loadingContent() {
    item {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 6.dp,
            )
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = CopyR.string.localisation_searchresults_loading),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private fun LazyListScope.searchResultsContent(searchResults: List<SearchResult>) {
    item {
        Spacer(modifier = Modifier.padding(top = 16.dp))
    }
    items(searchResults.size) { position ->
        SearchResultCard(modifier = Modifier.padding(bottom = 8.dp), searchResult = searchResults[position], onClick = {})
    }
}

private fun LazyListScope.errorContent(throwable: Throwable) {
    item {
        Text(text = "Something went wrong: " + throwable)
    }
}

@DefaultPreviews
@Composable
internal fun SearchResultsLoadingPreview() {
    MgoTheme {
        SearchResultsScreenContent(
            viewState = SearchResultsViewState.Loading,
        )
    }
}
