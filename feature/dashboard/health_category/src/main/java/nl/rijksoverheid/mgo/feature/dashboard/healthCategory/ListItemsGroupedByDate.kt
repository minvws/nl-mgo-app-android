package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SeparatorsTimeline
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroup
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Suppress("ktlint:standard:function-naming")
fun LazyListScope.ListItemsGroupedByDate(
  listItemGroups: List<ListItemsGroup>,
  onClickListItem: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
) {
  val listItemGroupsWithDate = listItemGroups.filter { group -> group.heading != null }
  val listItemGroupWithoutDate = listItemGroups.firstOrNull { group -> group.heading == null }
  listItemGroupsWithDate.forEachIndexed { groupIndex, listItemGroup ->
    val isLastItemGroup = groupIndex == listItemGroupsWithDate.lastIndex
    val borderCircle =
      when (groupIndex) {
        0 -> BorderCircle.Top
        listItemGroupsWithDate.lastIndex -> BorderCircle.Bottom
        else -> BorderCircle.Middle
      }
    item {
      Text(
        modifier =
          Modifier
            .timeline(borderCircle)
            .padding(start = 16.dp),
        text = listItemGroup.heading ?: "",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.CategoriesRijkslint(),
      )
    }
    items(listItemGroup.items.size) { position ->
      val listItem = listItemGroup.items[position]
      val isLastItem = position == listItemGroup.items.lastIndex
      val isLastItemGroup = isLastItem && isLastItemGroup
      HealthCategoryCard(
        modifier =
          Modifier.then(if (isLastItemGroup) Modifier else Modifier.timeline(null)).padding(
            top = if (position == 0) 12.dp else 0.dp,
            start = 16.dp,
            bottom = if (isLastItem) 24.dp else 8.dp,
          ),
        title = listItem.title,
        subtitle = listItem.subtitle,
        detail = listItem.detail,
        onClick = { onClickListItem(listItem.organization, listItem.mgoResource.referenceId) },
      )
    }
  }

  if (listItemGroupWithoutDate != null) {
    item {
      Text(
        modifier =
          Modifier
            .timeline(BorderCircle.Middle, circleColor = MaterialTheme.colorScheme.LabelsSecondary(), hasLine = false)
            .padding(start = 16.dp),
        text = stringResource(CopyR.string.common_unknown_date),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
      )
    }

    items(listItemGroupWithoutDate.items.size) { position ->
      val listItem = listItemGroupWithoutDate.items[position]
      HealthCategoryCard(
        modifier = Modifier.padding(top = 8.dp),
        title = listItem.title,
        subtitle = listItem.subtitle,
        detail = listItem.detail,
        onClick = { onClickListItem(listItem.organization, listItem.mgoResource.referenceId) },
      )
    }
  }
}

private sealed class BorderCircle {
  data object Top : BorderCircle()

  data object Middle : BorderCircle()

  data object Bottom : BorderCircle()
}

@Composable
private fun Modifier.timeline(
  circle: BorderCircle?,
  hasLine: Boolean = true,
  circleColor: Color = MaterialTheme.colorScheme.CategoriesRijkslint(),
  lineColor: Color = MaterialTheme.colorScheme.SeparatorsTimeline(),
): Modifier =
  this.drawBehind {
    val width = 4.dp
    val x = width.toPx() / 2
    val circleRadius = width.toPx() * 2
    val lineTop =
      when (circle) {
        BorderCircle.Bottom -> 0f
        BorderCircle.Middle -> 0f
        BorderCircle.Top -> circleRadius
        null -> 0f
      }
    val lineBottom =
      when (circle) {
        BorderCircle.Bottom -> size.height - circleRadius
        BorderCircle.Middle -> size.height
        BorderCircle.Top -> size.height
        null -> size.height
      }

    if (hasLine) {
      drawLine(
        color = lineColor,
        start = Offset(x, lineTop),
        end = Offset(x, lineBottom),
        strokeWidth = 2.dp.toPx(),
      )
    }

    if (circle != null) {
      drawCircle(
        color = circleColor,
        radius = circleRadius,
        center = Offset(x, size.height / 2),
      )
    }
  }

@PreviewLightDark
@Composable
internal fun ListItemsGroupedByDatePreview() {
  val listItemGroups =
    listOf(
      TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_1,
      TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_2,
      TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_3,
      TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_4,
    )
  MgoTheme {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
      ListItemsGroupedByDate(listItemGroups = listItemGroups, onClickListItem = { _, _ -> })
    }
  }
}
