package nl.rijksoverheid.mgo.feature.localisation.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddedHealthCareOverviewScreen() {
    val navigationManager = LocalNavigationManager.current
    val viewModel: AddedHealthCareOverviewScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    AddedHealthCareOverviewScreenContent(
        viewState = viewState,
        onRemoveProvider = { provider ->
            viewModel.delete(provider)
        },
    )

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collectLatest {
            navigationManager.popBackStack()
        }
    }
}

@Composable
private fun AddedHealthCareOverviewScreenContent(
    viewState: AddedHealthCareOverviewScreenViewState,
    onRemoveProvider: (provider: HealthCareProvider) -> Unit,
) {
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
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                buttonText = stringResource(id = R.string.localisation_add_healthcareprovider_primary_button),
                onButtonClick = { navigationManager.navigate(NavigationScreen.Dashboard) },
                secondaryButtonText = stringResource(id = R.string.localisation_add_healthcareprovider_secondary_button),
                onSecondaryButtonClick = { navigationManager.navigate(NavigationScreen.Localisation.Start) },
            ) {
                Text(
                    text = stringResource(id = R.string.localisation_add_healthcareprovider_title),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(id = R.string.localisation_add_healthcareprovider_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                )

                viewState.providers.forEach { provider ->
                    RemoveHealthCareProviderCard(
                        modifier = Modifier.padding(bottom = 8.dp),
                        provider = provider,
                        onClick =
                        onRemoveProvider,
                    )
                }
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun AddedHealthCareOverviewScreenPreview() {
    MgoTheme {
        AddedHealthCareOverviewScreenContent(
            viewState = AddedHealthCareOverviewScreenViewState(providers = listOf(TEST_HEALTH_CARE_PROVIDER)),
            onRemoveProvider = {},
        )
    }
}
