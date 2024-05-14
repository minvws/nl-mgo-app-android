package nl.rijksoverheid.mgo.feature.localisation.stored

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
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
import nl.rijksoverheid.mgo.feature.localisation.navigation.LocalLocalisationNavigationManager
import nl.rijksoverheid.mgo.feature.localisation.navigation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun StoredHealthCareProvidersScreen(onLocalisationFinished: () -> Unit) {
    val viewModel: StoredHealthCareProvidersScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    // Remove provider dialog
    var removeProvider by remember { mutableStateOf<HealthCareProvider?>(null) }
    removeProvider?.let { provider ->
        RemoveProviderDialog(
            provider = provider,
            onDismissRequest = {
                removeProvider = null
            },
            onConfirmButton = {
                viewModel.delete(provider)
                removeProvider = null
            },
        )
    }

    StoredHealthCareProvidersScreenContent(
        viewState = viewState,
        onRemoveProvider = { provider ->
            removeProvider = provider
        },
        onLocalisationFinished = onLocalisationFinished,
    )
}

@Composable
private fun StoredHealthCareProvidersScreenContent(
    viewState: StoredHealthCareProvidersScreenViewState,
    onRemoveProvider: (provider: HealthCareProvider) -> Unit,
    onLocalisationFinished: () -> Unit,
) {
    val navigationManager = LocalLocalisationNavigationManager.current
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
        content = { innerPadding ->
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = R.string.localisation_add_healthcareprovider_primary_button),
                onButtonClick = onLocalisationFinished,
                secondaryButtonText = stringResource(id = R.string.localisation_add_healthcareprovider_secondary_button),
                onSecondaryButtonClick = { navigationManager.navigate(LocalisationNavigationScreen.Search) },
            ) {
                Text(
                    text = stringResource(id = R.string.localisation_add_healthcareprovider_title),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                val subtitleTextResource =
                    if (viewState.providers.isEmpty()) {
                        R.string.localisation_add_healthcareprovider_empty_subtitle
                    } else {
                        R.string.localisation_add_healthcareprovider_subtitle
                    }
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(id = subtitleTextResource),
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

@Composable
private fun RemoveProviderDialog(
    provider: HealthCareProvider,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.localistaion_add_healthcareprovider_remove_dialog_title, provider.name)) },
        text = { Text(text = stringResource(id = R.string.localistaion_add_healthcareprovider_remove_dialog_text)) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmButton()
                },
            ) {
                Text(stringResource(id = R.string.localistaion_add_healthcareprovider_remove_dialog_confirm_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(stringResource(id = R.string.localistaion_add_healthcareprovider_remove_dialog_dismiss_button))
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun StoredHealthCareProvidersScreenPreview() {
    MgoTheme {
        StoredHealthCareProvidersScreenContent(
            viewState = StoredHealthCareProvidersScreenViewState(providers = listOf(TEST_HEALTH_CARE_PROVIDER)),
            onRemoveProvider = {},
            onLocalisationFinished = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun RemoveProviderDialogPreview() {
    MgoTheme {
        RemoveProviderDialog(
            provider = TEST_HEALTH_CARE_PROVIDER,
            onDismissRequest = {},
            onConfirmButton = {},
        )
    }
}
