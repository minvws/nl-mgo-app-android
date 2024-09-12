package nl.rijksoverheid.mgo.feature.organization.removeOrganization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.notificationError
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun RemoveOrganizationScreen(
    providerId: String,
    providerName: String,
    onNavigateBack: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
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
            viewModel.delete(providerId)
        },
    )
}

@Composable
private fun RemoveOrganizationScreenContent(
    providerName: String,
    onNavigateBack: () -> Unit,
    onDeleteProvider: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.common_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            ColumnWithButtons(
                modifier =
                    Modifier
                        .padding(innerPadding),
                buttonText = stringResource(id = CopyR.string.remove_organization_no_cancel),
                secondaryButtonText = stringResource(id = CopyR.string.remove_organization_yes_delete),
                onButtonClick = onNavigateBack,
                onSecondaryButtonClick = onDeleteProvider,
            ) {
                Box(
                    modifier =
                        Modifier
                            .padding(top = 24.dp)
                            .size(102.dp)
                            .background(MaterialTheme.colors.notificationError(), CircleShape)
                            .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier =
                            Modifier
                                .size(61.dp),
                        painter = painterResource(id = R.drawable.ic_delete),
                        tint = MaterialTheme.colors.backgroundSecondary(),
                        contentDescription = null,
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(id = CopyR.string.remove_organization_heading, providerName),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = CopyR.string.remove_organization_subheading, providerName),
                    style = MaterialTheme.typography.bodyDefault,
                )
            }
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
