package nl.rijksoverheid.mgo.feature.organization.organization

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.supportFysiotherapeut
import nl.rijksoverheid.mgo.component.theme.supportTandarts
import nl.rijksoverheid.mgo.component.theme.supportVerpleeghuis
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun OrganizationScreen(
    provider: MgoOrganization,
    onNavigateBack: () -> Unit,
    onNavigateToMedicationuse: (provider: MgoOrganization) -> Unit,
    onNavigateToProblems: () -> Unit,
    onNavigateToLabResults: () -> Unit,
    onNavigateToRemoveProvider: () -> Unit,
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.common_previous),
                        )
                    }
                },
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
                    text = provider.name,
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = provider.category ?: "",
                    style = MaterialTheme.typography.bodySmall,
                )

                OrganizationRow(
                    modifier =
                        Modifier
                            .padding(top = 24.dp)
                            .clickable { onNavigateToMedicationuse(provider) },
                    icon = R.drawable.ic_medicine,
                    iconCircleColor = MaterialTheme.colors.supportVerpleeghuis(),
                    title = CopyR.string.organization_medicine_heading,
                    subtitle = CopyR.string.organization_medicine_subheading,
                )

                OrganizationRow(
                    modifier =
                        Modifier
                            .padding(top = 4.dp)
                            .clickable { onNavigateToProblems() },
                    icon = R.drawable.ic_complaints,
                    iconCircleColor = MaterialTheme.colors.supportTandarts(),
                    title = CopyR.string.organization_diagnosis_heading,
                    subtitle = CopyR.string.organization_diagnosis_subheading,
                )

                OrganizationRow(
                    modifier =
                        Modifier
                            .padding(top = 4.dp)
                            .clickable { onNavigateToLabResults() },
                    icon = R.drawable.ic_results,
                    iconCircleColor = MaterialTheme.colors.supportFysiotherapeut(),
                    title = CopyR.string.organization_lab_results_heading,
                    subtitle = CopyR.string.organization_lab_results_subheading,
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    text = stringResource(id = CopyR.string.common_settings),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colors.contentTertiary(),
                )

                OrganizationRow(
                    modifier =
                        Modifier
                            .padding(top = 16.dp)
                            .clickable { onNavigateToRemoveProvider() },
                    icon = R.drawable.ic_delete,
                    iconCircleColor = MaterialTheme.colors.notificationError(),
                    title = CopyR.string.organization_remove_organization_heading,
                    subtitle = CopyR.string.organization_remove_organization_subheading,
                )
            }
        },
    )
}

@Composable
private fun OrganizationRow(
    @DrawableRes icon: Int,
    iconCircleColor: Color,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier =
                    Modifier
                        .size(32.dp)
                        .background(iconCircleColor, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colors.backgroundSecondary(),
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = stringResource(id = title), style = MaterialTheme.typography.bodyDefault, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colors.contentTertiary(),
                )
            }
        }
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationScreenPreview() {
    MgoTheme {
        OrganizationScreen(
            provider = TEST_MGO_ORGANIZATION,
            onNavigateBack = {},
            onNavigateToMedicationuse = {},
            onNavigateToProblems = {},
            onNavigateToLabResults = {},
            onNavigateToRemoveProvider = {},
        )
    }
}
