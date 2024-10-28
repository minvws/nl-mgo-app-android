package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.contentTertiary
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
    MgoScaffold(
        appBarTitle = stringResource(viewState.title),
        onNavigateBack = onNavigateBack,
        content = {
            when (viewState.listItemsState) {
                is HealthCategoryScreenViewState.ListItemsState.Loaded ->
                    ListItemsContent(
                        title = viewState.title,
                        listItems = viewState.listItemsState.listItems,
                        onClickUiSchema = onClickUiSchema,
                        showErrorBanner = showErrorBanner,
                        onRetryClick = onRetry,
                        onDismissErrorBanner = { showErrorBanner = false },
                    )

                HealthCategoryScreenViewState.ListItemsState.Loading ->
                    LoadingContent(
                        title = viewState.title,
                    )

                is HealthCategoryScreenViewState.ListItemsState.NoData ->
                    NoDataContent(
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
private fun ColumnScope.LoadingContent(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
) {
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
    ) {
        if (showErrorBanner) {
            item {
                MgoBanner(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
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
                        .padding(bottom = 16.dp),
                title = listItem.title,
                subtitle = listItem.subtitle,
            )
        }
    }
}

@Composable
private fun ColumnScope.NoDataContent(
    @StringRes title: Int,
    showErrorBanner: Boolean,
    onRetryClick: () -> Unit,
    onDismissErrorBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showErrorBanner) {
        MgoBanner(
            modifier =
                Modifier
                    .fillMaxWidth(),
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
                .weight(1f)
                .padding(top = 16.dp),
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

@Composable
private fun HealthCategoryCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier = modifier) {
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
