package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalSnackBarPresenter
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.sentimentCritical
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can remove a health care provider.
 *
 * @param providerId The id of the health care provider.
 * @param providerName The name of the health care provider.
 * @param onNavigateBack Called when requested to navigate back.
 * @param onNavigateToDashboard Called when requested to navigate to the dashboard (root screen with bottombar).
 */
@Composable
fun RemoveOrganizationScreen(
    providerId: String,
    providerName: String,
    onNavigateBack: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val snackbarPresenter = LocalSnackBarPresenter.current
    val viewModel: RemoveOrganizationScreenViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.providerDeleted.collectLatest {
            onNavigateToDashboard()
        }
    }
    RemoveOrganizationScreenContent(
        providerName = providerName,
        onNavigateBack = onNavigateBack,
        onDeleteProvider = {
            viewModel.delete(snackbarPresenter, providerId)
        },
    )
}

@Composable
private fun RemoveOrganizationScreenContent(
    providerName: String,
    onNavigateBack: () -> Unit,
    onDeleteProvider: () -> Unit,
) {
    MgoScaffold(
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        onNavigateBack = onNavigateBack,
        primaryButtonText = stringResource(id = CopyR.string.remove_organization_no_cancel),
        onPrimaryButtonClick = onNavigateBack,
        secondaryButtonText = stringResource(id = CopyR.string.remove_organization_yes_delete),
        onSecondaryButtonClick = onDeleteProvider,
        content = {
            Box(
                modifier =
                    Modifier
                        .padding(top = TopAppBarDefaults.MediumAppBarCollapsedHeight)
                        .size(102.dp)
                        .background(MaterialTheme.colorScheme.sentimentCritical(), CircleShape)
                        .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier =
                        Modifier
                            .size(61.dp),
                    painter = painterResource(id = R.drawable.ic_delete),
                    tint = MaterialTheme.colorScheme.backgroundSecondary(),
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(id = CopyR.string.remove_organization_heading, providerName),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text =
                    stringResource(
                        id = CopyR.string.remove_organization_subheading,
                        providerName,
                    ),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))
        },
    )
}

@PreviewLightDark
@Composable
fun RemoveOrganizationScreenPreview() {
    MgoTheme {
        RemoveOrganizationScreenContent(
            providerName = "UMC Groningen",
            onNavigateBack = {},
            onDeleteProvider = {},
        )
    }
}
