package nl.rijksoverheid.mgo.component.collapsablecard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingExtraSmall

@Composable
fun CollapsableCard(
    title: String,
    items: List<CollapsableCardItem>,
    modifier: Modifier = Modifier,
) {
    CollapsableCardContent(modifier = modifier, title = title, items = items, isCollapsed = true)
}

@Composable
private fun CollapsableCardContent(
    title: String,
    items: List<CollapsableCardItem>,
    isCollapsed: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(text = title.uppercase(), style = MaterialTheme.typography.headingExtraSmall)
            for (item in items) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = item.title.uppercase(),
                    color = MaterialTheme.colors.contentTertiary(),
                    style = MaterialTheme.typography.bodySmallMini,
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = item.value,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
internal fun CollapsableCardPreview() {
    MgoTheme {
        CollapsableCard(
            modifier = Modifier.fillMaxWidth(),
            title = "Card 1",
            items =
                listOf(
                    CollapsableCardItem(
                        title = "Header 1",
                        value = "Value 1",
                    ),
                    CollapsableCardItem(
                        title = "Header 2",
                        value = "Value 2",
                    ),
                ),
        )
    }
}
