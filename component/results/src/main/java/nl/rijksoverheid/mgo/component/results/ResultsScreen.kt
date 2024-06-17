package nl.rijksoverheid.mgo.component.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCard
import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCardItem
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.component.theme.R as ThemeR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun ResultsScreen(
    heading: String,
    subHeading: String,
    viewState: ResultsScreenViewState,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
                ResultsScreenViewState.Loading -> {
                    ResultsLoadingContent(
                        modifier = Modifier.padding(innerPadding),
                        heading = heading,
                        subHeading = subHeading,
                    )
                }

                is ResultsScreenViewState.Loaded -> {
                    ResultsLoadedContent(
                        heading = heading,
                        subHeading = subHeading,
                        viewState = viewState,
                    )
                }
            }
        },
    )
}

@Composable
private fun ResultsLoadingContent(
    heading: String,
    subHeading: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        ResultsHeader(heading = heading, subHeading = subHeading)
        ResultsLoading(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
        )
    }
}

@Composable
private fun ResultsLoadedContent(
    heading: String,
    subHeading: String,
    viewState: ResultsScreenViewState.Loaded,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(horizontal = 16.dp)) {
        item {
            ResultsHeader(heading = heading, subHeading = subHeading)
        }
        when (viewState) {
            is ResultsScreenViewState.Loaded.Success -> {
                if (viewState.cardItems.isEmpty()) {
                    item {
                        ResultsEmpty()
                    }
                } else {
                    items(viewState.cardItems.size) { position ->
                        val cardItem = viewState.cardItems[position]
                        CollapsableCard(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                            item = cardItem,
                        )
                    }
                }
            }

            is ResultsScreenViewState.Loaded.Error -> {
                item {
                    ResultsError(isProductionBuild = viewState.isProductionBuild, error = viewState.error)
                }
            }
        }
    }
}

@Composable
private fun ResultsHeader(
    heading: String,
    subHeading: String,
) {
    Text(
        text = heading,
        style = MaterialTheme.typography.headingLarge,
        fontWeight = FontWeight.Bold,
    )
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
        text = subHeading,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
private fun ResultsLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier =
                    Modifier
                        .size(48.dp),
                strokeWidth = 6.dp,
            )
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = CopyR.string.general_loading),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ResultsEmpty(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Image(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                painter = painterResource(id = ThemeR.drawable.illustration_woman_on_couch),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = CopyR.string.healthcareprovider_card_empty_title),
                style = MaterialTheme.typography.headingSmall,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = CopyR.string.healthcareprovider_card_empty_subtitle),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ResultsError(
    isProductionBuild: Boolean,
    error: Throwable,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Image(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                painter = painterResource(id = ThemeR.drawable.illustration_alert),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = CopyR.string.healthcareprovider_card_error_title),
                style = MaterialTheme.typography.headingSmall,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = CopyR.string.healthcareprovider_card_error_subtitle),
                style = MaterialTheme.typography.bodySmall,
            )
            if (!isProductionBuild) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = error.toString(),
                    style = MaterialTheme.typography.bodySmallMini,
                    color = MaterialTheme.colors.error,
                )
            }
        }
    }
}

@DefaultPreviews
@Composable
internal fun ResultsScreenLoadingPreview() {
    MgoTheme {
        ResultsScreen(
            heading = "Heading",
            subHeading = "Subheading",
            viewState = ResultsScreenViewState.Loading,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ResultsScreenEmptyPreview() {
    MgoTheme {
        ResultsScreen(
            heading = "Heading",
            subHeading = "Subheading",
            viewState = ResultsScreenViewState.Loaded.Success(listOf()),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ResultsScreenCardsPreview() {
    MgoTheme {
        ResultsScreen(
            heading = "Heading",
            subHeading = "Subheading",
            viewState =
                ResultsScreenViewState.Loaded.Success(
                    cardItems =
                        listOf(
                            CollapsableCardItem(
                                title = "Title 1",
                                properties = listOf(),
                            ),
                            CollapsableCardItem(
                                title = "Title 2",
                                properties = listOf(),
                            ),
                        ),
                ),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ResultsScreenErrorPreview() {
    MgoTheme {
        ResultsScreen(
            heading = "Heading",
            subHeading = "Subheading",
            viewState =
                ResultsScreenViewState.Loaded.Error(
                    error = IllegalStateException("Something went wrong"),
                    isProductionBuild = false,
                ),
            onNavigateBack = {},
        )
    }
}
