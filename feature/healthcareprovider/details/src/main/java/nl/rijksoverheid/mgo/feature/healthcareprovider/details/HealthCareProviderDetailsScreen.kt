package nl.rijksoverheid.mgo.feature.healthcareprovider.details
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthCareProviderDetailsScreen(
    providerName: String,
    onNavigateBack: () -> Unit,
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
                            contentDescription = stringResource(id = CopyR.string.general_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(all = 16.dp).padding(innerPadding)) {
                Text(
                    text = providerName,
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(id = CopyR.string.healthcareprovider_details_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun HealthCareProviderDetailsScreenPreview() {
    MgoTheme {
        HealthCareProviderDetailsScreen(
            providerName = "UMC Ziekenhuis",
            onNavigateBack = {},
        )
    }
}
