package nl.rijksoverheid.mgo.feature.healthcareprovider.details

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
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
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthCareProviderDetailsScreen(
    providerName: String,
    providerCategory: String,
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
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = providerName,
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = providerCategory,
                    style = MaterialTheme.typography.bodySmall,
                )

                ProviderRow(
                    modifier =
                        Modifier
                            .padding(top = 24.dp)
                            .clickable { },
                    icon = R.drawable.ic_medicine,
                    title = CopyR.string.healthcareprovider_details_list_item_medicine_title,
                    subtitle = CopyR.string.healthcareprovider_details_list_item_medicine_subtitle,
                )

                ProviderRow(
                    modifier =
                        Modifier
                            .padding(top = 4.dp)
                            .clickable { },
                    icon = R.drawable.ic_complaints,
                    title = CopyR.string.healthcareprovider_details_list_item_complaints_title,
                    subtitle = CopyR.string.healthcareprovider_details_list_item_complaints_subtitle,
                )

                ProviderRow(
                    modifier =
                        Modifier
                            .padding(top = 4.dp)
                            .clickable { },
                    icon = R.drawable.ic_results,
                    title = CopyR.string.healthcareprovider_details_list_item_results_title,
                    subtitle = CopyR.string.healthcareprovider_details_list_item_results_subtitle,
                )
            }
        },
    )
}

@Composable
private fun ProviderRow(
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = icon), contentDescription = null)
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
internal fun HealthCareProviderDetailsScreenPreview() {
    MgoTheme {
        HealthCareProviderDetailsScreen(
            providerName = "UMC Ziekenhuis",
            providerCategory = "Ziekenhuizen, medische centra en klinieken",
            onNavigateBack = {},
        )
    }
}
