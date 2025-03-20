package nl.rijksoverheid.mgo.feature.settings.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
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
fun SettingsScreen(
    onNavigateToDisplaySettings: () -> Unit,
    onNavigateToSecuritySettings: () -> Unit,
    onNavigateToAboutThisAppSettings: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    SettingsScreenContent()
}

@Composable
private fun SettingsScreenContent() {
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_heading),
        content = {
        },
    )
}
