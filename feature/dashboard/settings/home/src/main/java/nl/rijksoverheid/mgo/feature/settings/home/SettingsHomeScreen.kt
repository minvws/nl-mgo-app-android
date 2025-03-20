package nl.rijksoverheid.mgo.feature.settings.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can change different settings of the app.
 *
 * @param onNavigateToDisplaySettings Called when requested to navigate to the screen that shows display settings.
 * @param onNavigateToSecuritySettings Called when requested to navigate to the screen that shows security settings.
 * @param onNavigateToAboutThisAppSettings Called when requested to navigate to the screen that shows about this app settings.
 * @param onNavigateToOnboarding Called when requested to navigate to the onboarding.
 */
@Composable
fun SettingsHomeScreen(
    onNavigateToDisplaySettings: () -> Unit,
    onNavigateToSecuritySettings: () -> Unit,
    onNavigateToAboutThisAppSettings: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    SettingsScreenContent(
        onClickDisplaySettings = onNavigateToDisplaySettings,
    )
}

@Composable
private fun SettingsScreenContent(onClickDisplaySettings: () -> Unit) {
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_heading),
        content = {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(CopyR.string.settings_preferences_heading),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.contentSecondary(),
            )

            MgoCard(
                modifier =
                    Modifier
                        .padding(top = 12.dp),
            ) {
                SettingsListItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onClickDisplaySettings() },
                    icon = Icons.Outlined.LightMode,
                    heading = CopyR.string.settings_display_heading,
                    subHeading = CopyR.string.settings_display_light,
                )
            }
        },
    )
}

@Composable
private fun SettingsListItem(
    icon: ImageVector,
    @StringRes heading: Int,
    @StringRes subHeading: Int? = null,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.symbolsPrimary(),
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = stringResource(heading),
                style = MaterialTheme.typography.bodyMedium,
            )
            if (subHeading != null) {
                Text(
                    text = stringResource(subHeading),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.contentSecondary(),
                )
            }
        }
    }
}

@DefaultPreviews
@Composable
private fun SettingsHomeScreenPreview() {
    MgoTheme {
        SettingsScreenContent(
            onClickDisplaySettings = {},
        )
    }
}
