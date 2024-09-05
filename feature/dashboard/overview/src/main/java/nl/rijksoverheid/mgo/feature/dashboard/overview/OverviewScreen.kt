package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.overview.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_OVERVIEW_ORGANIZATION_CARD = "OVERVIEW_ORGANIZATION_CARD"

@Composable
fun OverviewScreen(
    onNavigateToLocalisation: () -> Unit,
    onNavigateToOrganization: (provider: MgoOrganization) -> Unit,
) {
    val viewModel: OverviewScreenViewModel = hiltViewModel()
    val viewState: OverviewScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    OverviewScreenContent(viewState = viewState, onNavigateToLocalisation = onNavigateToLocalisation)
}

@Composable
private fun OverviewScreenContent(
    viewState: OverviewScreenViewState,
    onNavigateToLocalisation: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            )
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = CopyR.string.overview_heading),
                    style = MaterialTheme.typography.headingLarge,
                )
                if (viewState.providers.isEmpty()) {
                    NoProviders(onNavigateToLocalisation)
                } else {
                    WithProviders(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
                }
            }
        },
    )
}

@Composable
private fun NoProviders(
    onNavigateToLocalisation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ColumnWithButtons(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        buttonText = stringResource(id = CopyR.string.overview_add_organization),
        onButtonClick = onNavigateToLocalisation,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(156.dp),
            painter = painterResource(id = R.drawable.illustration_overview_empty),
            contentDescription = null,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
            text = stringResource(id = CopyR.string.overview_empty_heading),
            style = MaterialTheme.typography.headingSmall,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            text = stringResource(id = CopyR.string.overview_empty_subheading),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colors.contentTertiary(),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun WithProviders(modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Card {
            Column {
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    title = CopyR.string.health_category_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                )
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    title = CopyR.string.health_category_allergies,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                )
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    title = CopyR.string.health_category_measurements,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                )
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    title = CopyR.string.health_category_vaccinations,
                    hasDivider = false,
                )
            }
        }

        Card(modifier = Modifier.padding(top = 16.dp)) {
            Column {
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    title = CopyR.string.health_category_complaints,
                )
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    title = CopyR.string.health_category_treatments,
                )
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    title = CopyR.string.health_category_labresults,
                    hasDivider = false,
                )
            }
        }

        Card(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
            Column {
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    title = CopyR.string.health_category_reports,
                )
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    title = CopyR.string.health_category_documents,
                )
            }
        }
    }
}

@Composable
private fun OverviewListItem(
    @DrawableRes icon: Int,
    @ColorRes iconColor: Color,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    hasDivider: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconColor)
            Text(modifier = Modifier.padding(start = 16.dp), text = stringResource(id = title), style = MaterialTheme.typography.bodySmall)
        }
        if (hasDivider) {
            Divider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(0.33.dp)
                        .padding(start = 16.dp),
                color = MaterialTheme.colors.strokesPrimary(),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState = OverviewScreenViewState(name = "", providers = listOf()),
            onNavigateToLocalisation = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersPreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState = OverviewScreenViewState(name = "", providers = listOf(TEST_MGO_ORGANIZATION)),
            onNavigateToLocalisation = {},
        )
    }
}
