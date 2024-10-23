package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.banner.MgoBanner
import nl.rijksoverheid.mgo.component.banner.MgoBannerType
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.getTitle
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthCategoryScreen(
    arguments: HealthCategoryScreenArguments,
    onClickUiSchema: (toolbarTitle: String, uiSchema: UISchema) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<HealthCategoryScreenViewModel, HealthCategoryScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(arguments) },
        )
    val viewState by viewModel.viewState.collectAsState()
    val medicationDetailsToolbarTitle = stringResource(id = CopyR.string.medication_details_heading)
    HealthCategoryScreenContent(
        viewState = viewState,
        onClickUiSchema = { uiSchema ->
            onClickUiSchema(medicationDetailsToolbarTitle, uiSchema)
        },
        onRetry = { viewModel.retry() },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun HealthCategoryScreenContent(
    viewState: HealthCategoryScreenViewState,
    onRetry: () -> Unit,
    onClickUiSchema: (uiSchema: UISchema) -> Unit,
    onNavigateBack: () -> Unit,
) {
    var showErrorBanner by remember(viewState.showErrorBanner) { mutableStateOf(viewState.showErrorBanner) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.common_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            when (viewState.listItemsState) {
                is HealthCategoryScreenViewState.ListItemsState.Loaded ->
                    ListItemsContent(
                        modifier = Modifier.padding(innerPadding),
                        title = viewState.title,
                        listItems = viewState.listItemsState.listItems,
                        onClickUiSchema = onClickUiSchema,
                        showErrorBanner = showErrorBanner,
                        onRetryClick = onRetry,
                        onDismissErrorBanner = { showErrorBanner = false },
                    )

                HealthCategoryScreenViewState.ListItemsState.Loading ->
                    LoadingContent(
                        modifier = Modifier.padding(innerPadding),
                        title = viewState.title,
                    )

                is HealthCategoryScreenViewState.ListItemsState.NoData ->
                    NoDataContent(
                        modifier = Modifier.padding(innerPadding),
                        title = viewState.title,
                        showErrorBanner = showErrorBanner,
                        onRetryClick = onRetry,
                        onDismissErrorBanner = { showErrorBanner = false },
                    )
            }
        },
    )
}

@Composable
private fun LoadingContent(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.headingLarge,
            fontWeight = FontWeight.Bold,
        )

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
                    text = stringResource(id = CopyR.string.health_category_loading_heading),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
private fun ListItemsContent(
    @StringRes title: Int,
    listItems: List<HealthCategoryScreenListItem>,
    onClickUiSchema: (uiSchema: UISchema) -> Unit,
    showErrorBanner: Boolean,
    onRetryClick: () -> Unit,
    onDismissErrorBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        item {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.headingLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        if (showErrorBanner) {
            item {
                MgoBanner(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    type = MgoBannerType.WARNING,
                    heading = stringResource(id = CopyR.string.health_category_error_banner_heading),
                    subHeading = stringResource(id = CopyR.string.health_category_error_banner_subheading),
                    buttonText = stringResource(id = CopyR.string.health_category_error_banner_try_again),
                    onButtonClick = onRetryClick,
                    onDismiss = onDismissErrorBanner,
                )
            }
        }

        items(listItems.size) { position ->
            val listItem = listItems[position]
            HealthCategoryCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onClickUiSchema(listItem.uiSchema) }
                        .padding(top = 16.dp),
                title = listItem.title,
                subtitle = listItem.subtitle,
            )
        }
    }
}

@Composable
private fun NoDataContent(
    @StringRes title: Int,
    showErrorBanner: Boolean,
    onRetryClick: () -> Unit,
    onDismissErrorBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.headingLarge,
            fontWeight = FontWeight.Bold,
        )

        if (showErrorBanner) {
            MgoBanner(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                type = MgoBannerType.WARNING,
                heading = stringResource(id = CopyR.string.health_category_error_banner_heading),
                subHeading = stringResource(id = CopyR.string.health_category_error_banner_subheading),
                buttonText = stringResource(id = CopyR.string.health_category_error_banner_try_again),
                onButtonClick = onRetryClick,
                onDismiss = onDismissErrorBanner,
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .weight(1f),
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
                style = MaterialTheme.typography.headingSmall,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                text = stringResource(id = CopyR.string.health_category_empty_subheading),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.contentTertiary(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun HealthCategoryCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.contentSecondary(),
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
                HealthCategoryScreenViewState.initialState(HealthCareCategory.MEDICATIONS).copy(
                    title = HealthCareCategory.MEDICATIONS.getTitle(),
                    listItemsState = HealthCategoryScreenViewState.ListItemsState.Loading,
                ),
            onClickUiSchema = {},
            onRetry = {},
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenListItemsPreview() {
    MgoTheme {
        HealthCategoryScreenContent(
            viewState =
                HealthCategoryScreenViewState.initialState(HealthCareCategory.MEDICATIONS).copy(
                    title = HealthCareCategory.MEDICATIONS.getTitle(),
                    listItemsState =
                        HealthCategoryScreenViewState.ListItemsState.Loaded(
                            listItems =
                                listOf(
                                    TEST_LIST_ITEM_1,
                                    TEST_LIST_ITEM_2,
                                    TEST_LIST_ITEM_3,
                                ),
                        ),
                ),
            onClickUiSchema = {},
            onRetry = {},
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
                HealthCategoryScreenViewState.initialState(HealthCareCategory.MEDICATIONS).copy(
                    title = HealthCareCategory.MEDICATIONS.getTitle(),
                    listItemsState =
                        HealthCategoryScreenViewState.ListItemsState.Loaded(
                            listItems =
                                listOf(
                                    TEST_LIST_ITEM_1,
                                    TEST_LIST_ITEM_2,
                                    TEST_LIST_ITEM_3,
                                ),
                        ),
                    showErrorBanner = true,
                ),
            onClickUiSchema = {},
            onRetry = {},
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenNoDataPreview() {
    MgoTheme {
        HealthCategoryScreenContent(
            viewState =
                HealthCategoryScreenViewState.initialState(HealthCareCategory.MEDICATIONS).copy(
                    title = HealthCareCategory.MEDICATIONS.getTitle(),
                    listItemsState = HealthCategoryScreenViewState.ListItemsState.NoData,
                ),
            onClickUiSchema = {},
            onRetry = {},
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun HealthCategoryScreenNoDataWithErrorPreview() {
    MgoTheme {
        HealthCategoryScreenContent(
            viewState =
                HealthCategoryScreenViewState.initialState(HealthCareCategory.MEDICATIONS).copy(
                    title = HealthCareCategory.MEDICATIONS.getTitle(),
                    listItemsState = HealthCategoryScreenViewState.ListItemsState.NoData,
                    showErrorBanner = true,
                ),
            onClickUiSchema = {},
            onRetry = {},
            onNavigateBack = {},
        )
    }
}
