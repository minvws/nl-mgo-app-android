package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.healthCareCategory.getIcon
import nl.rijksoverheid.mgo.component.healthCareCategory.getIconColor
import nl.rijksoverheid.mgo.component.healthCareCategory.getTitle
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.sentimentCritical
import nl.rijksoverheid.mgo.component.theme.sentimentPositive
import nl.rijksoverheid.mgo.component.theme.symbolsTertiary
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

@Composable
internal fun HealthCategoryListItem(
  category: HealthCareCategoryId,
  state: HealthCategoryListItemState,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  hasDivider: Boolean = true,
  dragIcon: @Composable (() -> Unit)? = null,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
      IconButton(onClick) {
        val icon =
          when (state) {
            HealthCategoryListItemState.ADD -> Icons.Default.AddCircle
            HealthCategoryListItemState.REMOVE -> Icons.Default.RemoveCircle
          }
        val iconColor =
          when (state) {
            HealthCategoryListItemState.ADD -> MaterialTheme.colorScheme.sentimentPositive()
            HealthCategoryListItemState.REMOVE -> MaterialTheme.colorScheme.sentimentCritical()
          }
        Icon(imageVector = icon, contentDescription = null, tint = iconColor)
      }

      Icon(
        modifier = Modifier.padding(start = 8.dp),
        painter = painterResource(id = category.getIcon()),
        contentDescription = null,
        tint = category.getIconColor(),
      )
      Text(
        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
        text = stringResource(id = category.getTitle()),
        style = MaterialTheme.typography.bodyMedium,
      )
      dragIcon?.let { composable ->
        composable()
      }
    }
    if (hasDivider) {
      HorizontalDivider(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(start = 96.dp),
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoryListItemPreview() {
  MgoTheme {
    Column {
      HealthCategoryListItem(
        category = HealthCareCategoryId.MEDICATIONS,
        state = HealthCategoryListItemState.ADD,
        onClick = {},
      )
      HealthCategoryListItem(
        category = HealthCareCategoryId.APPOINTMENTS,
        state = HealthCategoryListItemState.REMOVE,
        hasDivider = false,
        onClick = {},
        dragIcon = {
          IconButton(
            onClick = {},
          ) {
            Icon(imageVector = Icons.Default.DragHandle, tint = MaterialTheme.colorScheme.symbolsTertiary(), contentDescription = null)
          }
        },
      )
    }
  }
}
