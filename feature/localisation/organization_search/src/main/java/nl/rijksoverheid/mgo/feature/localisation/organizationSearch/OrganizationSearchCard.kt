package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_ORGANIZATION_SEARCH_CARD = "ORGANIZATION_SEARCH_CARD"

@Composable
fun OrganizationSearchCard(
    searchResult: MgoOrganization,
    onClick: (searchResult: MgoOrganization) -> Unit,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .clickable { onClick(searchResult) }
                    .padding(top = 12.dp, start = 12.dp, bottom = 12.dp)
                    .testTag(TEST_TAG_ORGANIZATION_SEARCH_CARD),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = searchResult.name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                val address = searchResult.address
                if (address != null) {
                    Text(text = address, style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic)
                }
                if (searchResult.added) {
                    AddedText(modifier = Modifier.padding(top = 8.dp))
                }
            }
            if (!searchResult.added) {
                IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = { onClick(searchResult) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_result_card_add),
                        contentDescription = stringResource(id = CopyR.string.common_add).uppercase(),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun AddedText(modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.primary) {
        Row(modifier = modifier) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_result_card_added),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(id = CopyR.string.add_organization_already_added),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@PreviewLightDark
@Composable
internal fun OrganizationSearchCardNotAddedPreview() {
    MgoTheme {
        OrganizationSearchCard(
            searchResult = TEST_MGO_ORGANIZATION,
            onClick = { },
            modifier = Modifier.padding(all = 16.dp),
        )
    }
}

@PreviewLightDark
@Composable
internal fun OrganizationSearchCardAddedPreview() {
    MgoTheme {
        OrganizationSearchCard(
            searchResult = TEST_MGO_ORGANIZATION.copy(added = true),
            onClick = { },
            modifier = Modifier.padding(all = 16.dp),
        )
    }
}
