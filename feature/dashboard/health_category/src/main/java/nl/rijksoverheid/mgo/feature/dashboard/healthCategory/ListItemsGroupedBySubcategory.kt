package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroup

@Suppress("ktlint:standard:function-naming")
fun LazyListScope.ListItemsGroupedBySubcategory(
  listItemGroups: List<ListItemsGroup>,
  onClickListItem: (organization: MgoOrganization, referenceId: MgoResourceReferenceId) -> Unit,
) {
  for (listItemGroup in listItemGroups) {
    if (listItemGroup.items.isNotEmpty()) {
      item {
        Text(
          modifier = Modifier.padding(bottom = 8.dp),
          text = listItemGroup.heading ?: "",
          style = MaterialTheme.typography.headlineMedium,
        )
      }
      items(listItemGroup.items.size) { position ->
        val listItem = listItemGroup.items[position]
        HealthCategoryCard(
          modifier =
            Modifier
              .fillMaxWidth()
              .padding(bottom = 16.dp),
          title = listItem.title,
          subtitle = listItem.subtitle,
          detail = listItem.detail,
          onClick = { onClickListItem(listItem.organization, listItem.mgoResource.referenceId) },
        )
      }
    }
  }
}
