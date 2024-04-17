package nl.rijksoverheid.mgo.feature.onboarding

import android.view.View.generateViewId
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jeziellago.compose.markdowntext.MarkdownText
import nl.rijksoverheid.mgo.component.theme.ColumnWithButton
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefault
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingMedium
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal val VIEW_ID_TEXT_WITH_LINK = generateViewId()

@Composable
internal fun PrivacyOverviewScreen() {
    val navigationManager = LocalNavigationManager.current
    val viewModel: PrivacyOverviewScreenViewModel = hiltViewModel()
    PrivacyOverviewScreenContent {
        viewModel.setHasSeenOnboarding()
        navigationManager.navigate(NavigationScreen.Dashboard)
    }
}

@Composable
internal fun PrivacyOverviewScreenContent(onClickNext: () -> Unit) {
    val navigationManager = LocalNavigationManager.current
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
                            contentDescription = stringResource(id = CopyR.string.general_previous),
                        )
                    }
                },
            )
        },
        backgroundColor = Color.Transparent,
        content = { innerPadding ->
            ColumnWithButton(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                buttonText = stringResource(id = CopyR.string.general_next),
                onButtonClick = { onClickNext.invoke() },
            ) {
                Text(
                    text = stringResource(id = CopyR.string.privacy_overview_title),
                    style = MaterialTheme.typography.headingMedium,
                    fontWeight = FontWeight.Bold,
                )
                MarkdownText(
                    modifier = Modifier.padding(top = 16.dp),
                    viewId = VIEW_ID_TEXT_WITH_LINK,
                    markdown = stringResource(id = CopyR.string.privacy_overview_description),
                    style = MaterialTheme.typography.bodySmall,
                    linkColor = MaterialTheme.colors.actionTertiaryDefault(),
                )
                ListItem(
                    modifier = Modifier.padding(top = 16.dp),
                    icon = R.drawable.ic_privacy_overview_encrypted,
                    text = stringResource(id = nl.rijksoverheid.mgo.framework.copy.R.string.privacy_overview_item_1),
                )
                ListItem(
                    modifier = Modifier.padding(top = 24.dp),
                    icon = R.drawable.ic_privacy_overview_health_and_safety,
                    text = stringResource(id = nl.rijksoverheid.mgo.framework.copy.R.string.privacy_overview_item_2),
                )
                ListItem(
                    modifier = Modifier.padding(top = 24.dp),
                    icon = R.drawable.ic_privacy_overview_verified_user,
                    text = stringResource(id = nl.rijksoverheid.mgo.framework.copy.R.string.privacy_overview_item_3),
                )
                ListItem(
                    modifier = Modifier.padding(top = 24.dp),
                    icon = R.drawable.ic_privacy_overview_gpp_bad,
                    text = stringResource(id = nl.rijksoverheid.mgo.framework.copy.R.string.privacy_overview_item_4),
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
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@DefaultPreviews
@Composable
internal fun PrivacyOverviewScreenPreview() {
    MgoTheme {
        PrivacyOverviewScreenContent { }
    }
}
