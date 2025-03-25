package nl.rijksoverheid.mgo.feature.settings.about.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.borderSecondary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary
import nl.rijksoverheid.mgo.feature.settings.about.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can see various information about the app.
 *
 * @param onNavigateToSecureUse Called when requested to navigate to the secure use screen.
 * @param onNavigateToOpenSource Called when requested to navigate to the open source screen.
 * @param onNavigateToAccessibility Called when requested to navigate to the accessibility info screen.
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun SettingsAboutHomeScreen(
    onNavigateToSecureUse: () -> Unit,
    onNavigateToOpenSource: () -> Unit,
    onNavigateToAccessibility: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    SettingsAboutHomeScreenContent(
        viewState = SettingsAboutHomeScreenViewState(appVersionCode = "1", appVersionName = "1.0.0", fhirParserVersion = ""),
        onClickSecureUse = onNavigateToSecureUse,
        onClickOpenSource = onNavigateToOpenSource,
        onClickAccessibility = onNavigateToAccessibility,
        onClickBack = onNavigateBack,
    )
}

@Composable
private fun SettingsAboutHomeScreenContent(
    viewState: SettingsAboutHomeScreenViewState,
    onClickSecureUse: () -> Unit,
    onClickOpenSource: () -> Unit,
    onClickAccessibility: () -> Unit,
    onClickBack: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_about_this_app_heading),
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        onNavigateBack = onClickBack,
        isAlwaysCollapsed = true,
        content = {
            MgoCard(
                modifier =
                    Modifier
                        .padding(top = 8.dp),
            ) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.illustration_vws),
                    contentDescription = null,
                )
                SettingsAboutHomeListItem(
                    modifier = Modifier.padding(top = 16.dp),
                    heading = CopyR.string.common_app_name,
                    headingBold = true,
                    subHeading = "Versie 1.0.0 (12345)",
                )
                SettingsAboutHomeListItem(
                    heading = CopyR.string.settings_about_this_app_open_source,
                )
                SettingsAboutHomeListItem(
                    heading = CopyR.string.settings_about_this_app_accessibility,
                    hasDivider = false,
                )
            }

            MgoCard(
                modifier =
                    Modifier
                        .padding(top = 32.dp),
            ) {
                SettingsAboutHomeListItem(
                    heading = CopyR.string.settings_about_this_app_privacy,
                    hasDivider = false,
                    icon = Icons.AutoMirrored.Default.OpenInNew,
                )
            }
        },
    )
}

@Composable
private fun SettingsAboutHomeListItem(
    @StringRes heading: Int,
    headingBold: Boolean = false,
    icon: ImageVector? = null,
    subHeading: String? = null,
    hasDivider: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = stringResource(heading),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (headingBold) FontWeight.Bold else FontWeight.Normal,
                )
                if (subHeading != null) {
                    Text(
                        text = subHeading,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.contentSecondary(),
                    )
                }
            }

            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.symbolsPrimary(),
                )
            }
        }
        if (hasDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 32.dp),
                color = MaterialTheme.colorScheme.borderSecondary(),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun SettingsAboutHomeScreenPreview() {
    MgoTheme {
        SettingsAboutHomeScreenContent(
            viewState =
                SettingsAboutHomeScreenViewState(
                    appVersionCode = "1",
                    appVersionName = "1.0.0",
                    fhirParserVersion =
                        "{ \"version\": \"main\", \"git_ref\": \"d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705\"," +
                            " \"created\": \"2025-03-21T16:01:38\"}",
                ),
            onClickSecureUse = {},
            onClickOpenSource = {},
            onClickAccessibility = {},
            onClickBack = {},
        )
    }
}
