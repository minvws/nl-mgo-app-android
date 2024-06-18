package nl.rijksoverheid.mgo.component.collapsablecard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun CollapsableCard(
    item: CollapsableCardItem,
    modifier: Modifier = Modifier,
) {
    val initialCollapsed = !LocalInspectionMode.current
    var collapsed by rememberSaveable { mutableStateOf(initialCollapsed) }
    CollapsableCardContent(
        modifier = modifier,
        item = item,
        isCollapsed = collapsed,
        onCollapsed = { collapsed = it },
    )
}

@Composable
private fun CollapsableCardContent(
    item: CollapsableCardItem,
    isCollapsed: Boolean,
    onCollapsed: (collapsed: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier = modifier) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 10.dp, bottom = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.title,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = { onCollapsed(!isCollapsed) }) {
                    val iconRotation = if (isCollapsed) 0f else 180f
                    Icon(
                        modifier = Modifier.rotate(iconRotation),
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription =
                            if (isCollapsed) {
                                stringResource(
                                    id =
                                        CopyR.string
                                            .healthcareprovider_card_collapsed_arrow_content_description,
                                )
                            } else {
                                stringResource(
                                    id =
                                        CopyR.string
                                            .healthcareprovider_card_expanded_arrow_content_description,
                                )
                            },
                    )
                }
            }
            if (!isCollapsed) {
                for (property in item.properties) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = property.heading.uppercase(),
                        color = MaterialTheme.colors.contentTertiary(),
                        style = MaterialTheme.typography.bodySmallMini,
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = property.value,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
internal fun CollapsableCardCollapsedPreview() {
    MgoTheme {
        CollapsableCardContent(
            modifier = Modifier.fillMaxWidth(),
            item =
                CollapsableCardItem(
                    title = "Card 1",
                    properties =
                        listOf(
                            CollapsableCardProperty(
                                heading = "Header 1",
                                value = "Value 1",
                            ),
                            CollapsableCardProperty(heading = "Header 2", value = "Value 2"),
                        ),
                ),
            isCollapsed = true,
            onCollapsed = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun CollapsableCardExpandedPreview() {
    MgoTheme {
        CollapsableCardContent(
            modifier = Modifier.fillMaxWidth(),
            item =
                CollapsableCardItem(
                    title = "Card 1",
                    properties =
                        listOf(
                            CollapsableCardProperty(
                                heading = "Header 1",
                                value = "Value 1",
                            ),
                            CollapsableCardProperty(heading = "Header 2", value = "Value 2"),
                        ),
                ),
            isCollapsed = false,
            onCollapsed = {},
        )
    }
}
