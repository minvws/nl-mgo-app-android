package nl.rijksoverheid.mgo.feature.onboarding.proposition

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoHtmlText
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.framework.navigation.launchBrowser
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PropositionOverviewScreen(
    onNavigateBack: () -> Unit,
    onOnboardingFinished: () -> Unit,
) {
    val viewModel: PropositionScreenViewModel = hiltViewModel()
    PropositionOverviewScreenContent(
        url = viewModel.getUrl(),
        onNavigateBack = onNavigateBack,
        onClickNext = {
            viewModel.setHasSeenOnboarding()
            onOnboardingFinished()
        },
    )
}

@Composable
internal fun PropositionOverviewScreenContent(
    url: String,
    onNavigateBack: () -> Unit,
    onClickNext: () -> Unit,
) {
    val context = LocalContext.current
    MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.proposition_heading),
        onNavigateBack = onNavigateBack,
        content = {
            ColumnWithButtons(
                buttonText = stringResource(id = CopyR.string.common_next),
                onButtonClick = { onClickNext.invoke() },
            ) {
                MgoHtmlText(
                    html = stringResource(id = CopyR.string.proposition_subheading, url),
                    style = MaterialTheme.typography.bodySmall,
                    onLinkClicked = { url -> url.launchBrowser(context) },
                )
                ListItem(
                    modifier = Modifier.padding(top = 16.dp),
                    icon = R.drawable.ic_privacy_overview_encrypted,
                    text = stringResource(id = CopyR.string.proposition_statement_1),
                )
                ListItem(
                    modifier = Modifier.padding(top = 24.dp),
                    icon = R.drawable.ic_privacy_overview_health_and_safety,
                    text = stringResource(id = CopyR.string.proposition_statement_2),
                )
                ListItem(
                    modifier = Modifier.padding(top = 24.dp),
                    icon = R.drawable.ic_privacy_overview_verified_user,
                    text = stringResource(id = CopyR.string.proposition_statement_3),
                )
                ListItem(
                    modifier = Modifier.padding(top = 24.dp),
                    icon = R.drawable.ic_privacy_overview_gpp_bad,
                    text = stringResource(id = CopyR.string.proposition_statement_4),
                )
            }
        },
    )
}

@Composable
private fun ListItem(
    @DrawableRes icon: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Image(painter = painterResource(id = icon), contentDescription = null)
        MgoHtmlText(
            modifier = Modifier.padding(horizontal = 16.dp),
            html = text,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@DefaultPreviews
@Composable
internal fun PropositionScreenPreview() {
    MgoTheme {
        PropositionOverviewScreenContent(
            url = "https://www.google.nl",
            onNavigateBack = {},
            onClickNext = {},
        )
    }
}
