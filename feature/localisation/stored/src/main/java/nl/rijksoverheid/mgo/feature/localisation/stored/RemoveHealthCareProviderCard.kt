package nl.rijksoverheid.mgo.feature.localisation.stored

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun RemoveHealthCareProviderCard(
    provider: HealthCareProvider,
    onClick: (provider: HealthCareProvider) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier, shape = RoundedCornerShape(8.dp)) {
        Row(
            modifier =
                Modifier
                    .clickable { onClick(provider) }
                    .padding(top = 12.dp, start = 12.dp, bottom = 12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val category = provider.category ?: stringResource(id = CopyR.string.general_unknown)
                Text(text = category, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                Text(text = provider.name, style = MaterialTheme.typography.bodySmall)
                val address = provider.address
                if (address != null) {
                    Text(text = address, style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic)
                }
            }
            IconButton(onClick = { onClick(provider) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove_healthcare_provider_card_remove),
                    contentDescription = stringResource(id = CopyR.string.general_remove),
                    tint = MaterialTheme.colors.iconsPrimary(),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
internal fun RemoveHealthCareProviderCardPreview() {
    MgoTheme {
        RemoveHealthCareProviderCard(
            provider = TEST_HEALTH_CARE_PROVIDER,
            onClick = { },
            modifier = Modifier.padding(all = 16.dp),
        )
    }
}
