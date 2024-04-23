package nl.rijksoverheid.mgo.feature.localisation.searchresults

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.feature.localisation.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SearchResultCard(
    searchResult: HealthCareProvider,
    onClick: (searchResult: HealthCareProvider) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier, shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.clickable { onClick(searchResult) }.padding(top = 12.dp, start = 12.dp, bottom = 12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = searchResult.name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                val address = searchResult.address
                if (address != null) {
                    Text(text = address, style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic)
                }
            }
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = { onClick(searchResult) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_result_card_add),
                    contentDescription = stringResource(id = CopyR.string.general_add),
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
internal fun SearchResultCardPreview() {
    MgoTheme {
        SearchResultCard(
            searchResult = TEST_HEALTH_CARE_PROVIDER,
            onClick = { },
            modifier = Modifier.padding(all = 16.dp),
        )
    }
}
