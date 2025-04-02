package nl.rijksoverheid.mgo.feature.settings.about.accessibility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SettingsAboutAccessibilityScreen(onNavigateBack: () -> Unit) {
    val viewModel = hiltViewModel<SettingsAboutAccessibilityScreenViewModel>()
    SettingsAboutAccessibilityScreenContent(
        url = stringResource(viewModel.getUrl()),
        onClickBack = onNavigateBack,
    )
}

@Composable
private fun SettingsAboutAccessibilityScreenContent(
    url: String,
    onClickBack: () -> Unit,
) {
    val context = LocalContext.current
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_accessibility_heading),
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(CopyR.string.settings_accessibility_subheading),
                    )

                    // Button disabled for now, since there is no decision if a url is needed
//                    MgoButton(
//                        modifier = Modifier.padding(top = 16.dp),
//                        buttonText = stringResource(CopyR.string.settings_accessibility_more_information),
//                        onClick = { context.launchBrowser(url) },
//                        buttonTheme = MgoButtonTheme.LINK,
//                    )
                }
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun SettingsAboutAccessibilityScreenPreview() {
    MgoTheme {
        SettingsAboutAccessibilityScreenContent(
            url = "",
            onClickBack = {},
        )
    }
}
