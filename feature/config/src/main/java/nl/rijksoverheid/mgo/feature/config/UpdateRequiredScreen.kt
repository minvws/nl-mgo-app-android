package nl.rijksoverheid.mgo.feature.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingMedium
import nl.rijksoverheid.mgo.framework.navigation.launchBrowser
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_UPDATE_REQUIRED_TITLE = "UPDATE_REQUIRED_TITLE"

@Composable
fun UpdateRequiredScreen(packageName: String = LocalContext.current.packageName) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") }, backgroundColor = Color.Transparent, elevation = 0.dp)
        },
        content = { innerPadding ->
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = CopyR.string.update_required_download),
                onButtonClick = { "https://play.google.com/store/apps/details?id=$packageName".launchBrowser(context) },
            ) {
                Image(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.illustration_old_version),
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.padding(top = 32.dp).testTag(TEST_TAG_UPDATE_REQUIRED_TITLE),
                    text = "Je hebt een oude versie van de app",
                    style = MaterialTheme.typography.headingMedium,
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = CopyR.string.update_required_subheading),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun UpdateRequiredScreenPreview() {
    MgoTheme {
        UpdateRequiredScreen()
    }
}
