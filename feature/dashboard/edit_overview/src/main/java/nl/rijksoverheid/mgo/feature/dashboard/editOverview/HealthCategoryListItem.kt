package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.healthCategories.getColor
import nl.rijksoverheid.mgo.component.healthCategories.getDrawable
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.component.theme.StatesPositive
import nl.rijksoverheid.mgo.component.theme.SymbolsTertiary
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_ALLERGIES
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS

@Composable
internal fun HealthCategoryListItem(
  category: HealthCategoryGroup.HealthCategory,
  state: HealthCategoryListItemState,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  dragHandleModifier: Modifier? = null,
  hasDivider: Boolean = true,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
      IconButton(onClick) {
        val icon =
          when (state) {
            HealthCategoryListItemState.ADD -> Icons.Default.AddCircle
            HealthCategoryListItemState.REMOVE -> Icons.Default.RemoveCircle
          }
        val iconColor =
          when (state) {
            HealthCategoryListItemState.ADD -> MaterialTheme.colorScheme.StatesPositive()
            HealthCategoryListItemState.REMOVE -> MaterialTheme.colorScheme.StatesCritical()
          }
        Icon(imageVector = icon, contentDescription = null, tint = iconColor)
      }
      Box(
        modifier =
          Modifier
            .size(32.dp)
            .background(color = category.icon.getColor().copy(alpha = 0.15f), shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
      ) {
        Icon(
          painter = painterResource(id = category.icon.getDrawable()),
          contentDescription = null,
          tint = category.icon.getColor(),
        )
      }
      Text(
        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
        text = LocalContext.current.getString(category.heading),
        style = MaterialTheme.typography.bodyMedium,
      )
      if (dragHandleModifier != null) {
        IconButton(
          modifier = dragHandleModifier,
          onClick = {},
        ) {
          Icon(imageVector = Icons.Default.DragHandle, tint = MaterialTheme.colorScheme.SymbolsTertiary(), contentDescription = null)
        }
      }
    }
    if (hasDivider) {
      HorizontalDivider(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(start = 88.dp),
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
        category = TEST_HEALTH_CATEGORY_PROBLEMS,
        state = HealthCategoryListItemState.ADD,
        onClick = {},
      )
      HealthCategoryListItem(
        category = TEST_HEALTH_CATEGORY_ALLERGIES,
        state = HealthCategoryListItemState.REMOVE,
        hasDivider = false,
        onClick = {},
      )
    }
  }
}
