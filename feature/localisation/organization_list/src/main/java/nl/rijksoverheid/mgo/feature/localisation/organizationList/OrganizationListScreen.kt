package nl.rijksoverheid.mgo.feature.localisation.organizationList

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun OrganizationListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddOrganization: () -> Unit,
    onLocalisationFinished: () -> Unit,
) {
    val viewModel: OrganizationListScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    // Remove provider dialog
    var removeProvider by remember { mutableStateOf<MgoOrganization?>(null) }
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

    OrganizationListScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onRemoveProvider = { provider ->
            removeProvider = provider
        },
        onNavigateToSearch = onNavigateToAddOrganization,
        onLocalisationFinished = onLocalisationFinished,
    )
}

@Composable
private fun OrganizationListScreenContent(
    viewState: OrganizationListScreenViewState,
    onNavigateBack: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onRemoveProvider: (provider: MgoOrganization) -> Unit,
    onLocalisationFinished: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.common_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = R.string.common_ready),
                onButtonClick = onLocalisationFinished,
                secondaryButtonText = stringResource(id = R.string.organization_list_add_organization),
                onSecondaryButtonClick = onNavigateToSearch,
            ) {
                Text(
                    text = stringResource(id = R.string.organization_list_heading),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                val subtitleTextResource =
                    if (viewState.providers.isEmpty()) {
                        R.string.organization_list_no_results_subheading
                    } else {
                        R.string.organization_list_subheading
                    }
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(id = subtitleTextResource),
                    style = MaterialTheme.typography.bodySmall,
                )

                viewState.providers.forEach { provider ->
                    RemoveOrganizationCard(
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
    provider: MgoOrganization,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.dialog_remove_organization_heading, provider.name)) },
        text = { Text(text = stringResource(id = R.string.dialog_remove_organization_subheading)) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmButton()
                },
            ) {
                Text(stringResource(id = R.string.dialog_remove_organization_yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(stringResource(id = R.string.dialog_remove_organization_no))
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun OrganizationListScreenPreview() {
    MgoTheme {
        OrganizationListScreenContent(
            viewState = OrganizationListScreenViewState(providers = listOf(TEST_MGO_ORGANIZATION)),
            onNavigateBack = {},
            onNavigateToSearch = {},
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
            provider = TEST_MGO_ORGANIZATION,
            onDismissRequest = {},
            onConfirmButton = {},
        )
    }
}
