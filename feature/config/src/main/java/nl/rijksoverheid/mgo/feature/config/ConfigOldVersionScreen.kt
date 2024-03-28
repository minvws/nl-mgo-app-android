package nl.rijksoverheid.mgo.feature.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.ColumnWithButton
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun ConfigOldVersionScreen() {
    val navigationManager = LocalNavigationManager.current
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            TopAppBar(title = { Text(text = "") }, backgroundColor = Color.Transparent, elevation = 0.dp)
        },
        backgroundColor = Color.Transparent,
        content = { innerPadding ->
            ColumnWithButton(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = CopyR.string.config_old_version_button),
                onButtonClick = { navigationManager.navigate(NavigationScreen.Onboarding.PrivacyOverview) },
            ) {
                Image(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.illustration_old_version),
                    contentDescription =
                    null,
                )

                Text(
                    modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    text = stringResource(id = CopyR.string.config_old_version_title),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    text = stringResource(id = CopyR.string.config_old_version_description),
                    style = MaterialTheme.typography.body2,
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun ConfigOldVersionScreenPreview() {
    MgoTheme {
        ConfigOldVersionScreen()
    }
}
