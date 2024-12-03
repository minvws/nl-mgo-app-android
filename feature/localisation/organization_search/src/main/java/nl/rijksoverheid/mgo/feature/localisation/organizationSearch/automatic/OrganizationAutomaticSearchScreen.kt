package nl.rijksoverheid.mgo.feature.localisation.organizationSearch.automatic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.composable.debugerror.MgoDebugErrorButton
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.localisation.organizationSearch.TEST_TAG_ORGANIZATION_SEARCH_CARD
import nl.rijksoverheid.mgo.feature.localisation.organizationSearch.getCardState
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.theme.R as ThemeR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun OrganizationAutomaticSearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val viewModel: OrganizationAutomaticSearchScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.navigation.collectLatest {
            onNavigateToDashboard()
        }
    }
    OrganizationAutomaticSearchScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onGetSearchResults = { viewModel.getSearchResults() },
        updateOrganization = { organization, added -> viewModel.updateOrganization(organization, added) },
    )
}

@Composable
private fun OrganizationAutomaticSearchScreenContent(
    viewState: OrganizationAutomaticSearchScreenViewState,
    onNavigateBack: () -> Unit,
    onGetSearchResults: () -> Unit,
    updateOrganization: (organization: MgoOrganization, added: Boolean) -> Unit,
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
                    // TODO: Empty state
                }

                else -> {
                    OrganizationSearchScreenContent(
                        searchResults = viewState.results,
                        updateOrganization = updateOrganization,
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
    updateOrganization: (organization: MgoOrganization, added: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(top = 2.dp)) {
        items(searchResults.size) { position ->
            val organization = searchResults[position]
            OrganizationAutomaticSearchCard(
                modifier =
                    Modifier
                        .padding(bottom = 8.dp)
                        .testTag(TEST_TAG_ORGANIZATION_SEARCH_CARD),
                organization = organization,
                onCheckedChange = { checked ->
                    updateOrganization(organization, checked)
                },
                cardState = organization.getCardState(),
            )
        }
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
internal fun OrganizationAutomaticSearchScreenLoadingPreview() {
    MgoTheme {
        OrganizationAutomaticSearchScreenContent(
            viewState = OrganizationAutomaticSearchScreenViewState.initialState,
            onNavigateBack = {},
            onGetSearchResults = {},
            updateOrganization = { _, _ -> },
        )
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationAutomaticSearchScreenSearchResultsPreview() {
    MgoTheme {
        OrganizationAutomaticSearchScreenContent(
            viewState =
                OrganizationAutomaticSearchScreenViewState.initialState.copy(
                    loading = false,
                    results = listOf(TEST_MGO_ORGANIZATION, TEST_MGO_ORGANIZATION, TEST_MGO_ORGANIZATION),
                ),
            onNavigateBack = {},
            onGetSearchResults = {},
            updateOrganization = { _, _ -> },
        )
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationAutomaticSearchScreenErrorPreview() {
    MgoTheme {
        OrganizationAutomaticSearchScreenContent(
            viewState =
                OrganizationAutomaticSearchScreenViewState.initialState.copy(
                    loading = false,
                    error = IllegalStateException("Something went wrong"),
                ),
            onNavigateBack = {},
            onGetSearchResults = {},
            updateOrganization = { _, _ -> },
        )
    }
}
