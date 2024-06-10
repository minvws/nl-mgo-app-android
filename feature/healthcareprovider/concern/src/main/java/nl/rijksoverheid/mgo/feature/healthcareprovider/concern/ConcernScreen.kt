package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCard
import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCardItem
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.data.concern.models.TEST_MGO_CONCERN
import nl.rijksoverheid.mgo.component.theme.R as ThemeR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun ConcernScreen(onNavigateBack: () -> Unit) {
    val viewModel: ConcernScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    ConcernScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun ConcernScreenContent(
    viewState: ConcernScreenViewState,
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
            LazyColumn(modifier = Modifier.padding(innerPadding), contentPadding = PaddingValues(horizontal = 16.dp)) {
                item {
                    Text(
                        text = stringResource(id = CopyR.string.concern_title),
                        style = MaterialTheme.typography.headingLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
                        text = stringResource(id = CopyR.string.concern_subtitle),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                when (viewState) {
                    is ConcernScreenViewState.Loading -> {
                        item {
                            ConcernScreenLoadingContent(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                        .padding(bottom = 16.dp),
                            )
                        }
                    }

                    is ConcernScreenViewState.Success -> {
                        items(viewState.concerns.size) { position ->
                            val concern = viewState.concerns[position]
                            CollapsableCard(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                title = concern.title,
                                items =
                                    listOf(
                                        CollapsableCardItem(
                                            title = stringResource(id = CopyR.string.healthcareprovider_card_start_category_header),
                                            value = concern.category,
                                        ),
                                        CollapsableCardItem(
                                            title = stringResource(id = CopyR.string.healthcareprovider_card_begin_date_header),
                                            value = concern.startDate,
                                        ),
                                        CollapsableCardItem(
                                            title = stringResource(id = CopyR.string.healthcareprovider_card_end_date_header),
                                            value = concern.endDate,
                                        ),
                                        CollapsableCardItem(
                                            title = stringResource(id = CopyR.string.healthcareprovider_card_body_location_header),
                                            value = concern.bodyLocation,
                                        ),
                                        CollapsableCardItem(
                                            title = stringResource(id = CopyR.string.healthcareprovider_card_comment_header),
                                            value = concern.comment,
                                        ),
                                    ),
                            )
                        }
                    }

                    is ConcernScreenViewState.Error -> {
                        item {
                            ConcernScreenErrorContent(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                isProductionBuild = viewState.isProductionBuild,
                                error = viewState.error,
                            )
                        }
                    }
                }
            }
        },
    )
}

@Composable
private fun ConcernScreenLoadingContent(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
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
private fun ConcernScreenErrorContent(
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
internal fun ConcernScreenLoadingPreview() {
    MgoTheme {
        ConcernScreenContent(
            viewState = ConcernScreenViewState.Loading,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ConcernScreenConcernsPreview() {
    MgoTheme {
        ConcernScreenContent(
            viewState = ConcernScreenViewState.Success(listOf(TEST_MGO_CONCERN, TEST_MGO_CONCERN)),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ConcernScreenErrorPreview() {
    MgoTheme {
        ConcernScreenContent(
            viewState = ConcernScreenViewState.Error(isProductionBuild = true, error = IllegalStateException("Something went wrong")),
            onNavigateBack = {},
        )
    }
}
