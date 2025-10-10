package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.healthCategories.getIcon
import nl.rijksoverheid.mgo.component.healthCategories.getIconColor
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object HealthCategoriesListItemTestTag {
  const val LIST_ITEM = "HealthCategoriesListItem"
}

@Composable
internal fun HealthCategoriesListItem(
  filterOrganization: MgoOrganization?,
  category: HealthCategoryGroup.HealthCategory,
  modifier: Modifier = Modifier,
  hasDivider: Boolean = true,
) {
  if (LocalInspectionMode.current) {
    HealthCategoriesListItemContent(
      modifier = modifier,
      category = category,
      listItemState = HealthCategoriesListItemState.LOADED,
      hasDivider = hasDivider,
    )
  } else {
    val viewModel =
      hiltViewModel<HealthCategoriesListItemViewModel, HealthCategoriesListItemViewModel.Factory>(
        creationCallback = { factory -> factory.create(filterOrganization = filterOrganization, category = category) },
        key = category.toString(),
      )
    val listItemState by viewModel.listItemState.collectAsState()
    HealthCategoriesListItemContent(
      modifier = modifier,
      category = category,
      listItemState = listItemState,
      hasDivider = hasDivider,
    )
  }
}

@Composable
internal fun HealthCategoriesListItemContent(
  category: HealthCategoryGroup.HealthCategory,
  listItemState: HealthCategoriesListItemState,
  modifier: Modifier = Modifier,
  hasDivider: Boolean = true,
) {
  Column(modifier = modifier.fillMaxWidth().testTag(HealthCategoriesListItemTestTag.LIST_ITEM)) {
    Row(modifier = Modifier.padding(16.dp)) {
      Box(
        modifier = Modifier.size(32.dp).background(color = category.getIconColor().copy(alpha = 0.15f), shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
      ) {
        Icon(painter = painterResource(id = category.getIcon()), contentDescription = null, tint = category.getIconColor())
      }
      Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
        Text(
          text = LocalContext.current.getString(category.heading),
          style =
            MaterialTheme.typography
              .bodyMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.contentPrimary(),
        )
        val subheadingText =
          if (listItemState == HealthCategoriesListItemState.NO_DATA) {
            stringResource(CopyR.string.common_no_data)
          } else {
            LocalContext.current.getString(category.subheading)
          }
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = subheadingText,
          style =
            MaterialTheme.typography
              .bodyMedium,
          color = MaterialTheme.colorScheme.contentSecondary(),
        )
      }
      if (listItemState == HealthCategoriesListItemState.LOADING) {
        CircularProgressIndicator(
          modifier =
            Modifier
              .size(24.dp)
              .padding(start = 8.dp),
          strokeWidth = 2.dp,
          trackColor = MaterialTheme.colorScheme.backgroundTertiary().copy(alpha = 0.5f),
          color = MaterialTheme.colorScheme.symbolsSecondary(),
        )
      } else {
        Box(modifier = Modifier.size(24.dp))
      }
    }
    if (hasDivider) {
      HorizontalDivider(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(start = 58.dp),
      )
    }
  }
}

// @PreviewLightDark
// @Composable
// internal fun HealthCategoriesListItemLoadingPreview() {
//  MgoTheme {
//    HealthCategoriesListItemContent(
//      icon = HealthCategoryR.drawable.ic_medication,
//      title = CopyR.string.hc_medication_heading,
//      iconColor = MaterialTheme.colorScheme.supportContacts(),
//      listItemState = HealthCategoriesListItemState.LOADING,
//    )
//  }
// }
//
// @PreviewLightDark
// @Composable
// internal fun HealthCategoriesListItemNoDataPreview() {
//  MgoTheme {
//    HealthCategoriesListItemContent(
//      icon = HealthCategoryR.drawable.ic_medication,
//      title = CopyR.string.hc_medication_heading,
//      iconColor = MaterialTheme.colorScheme.supportContacts(),
//      listItemState = HealthCategoriesListItemState.NO_DATA,
//    )
//  }
// }
//
// @PreviewLightDark
// @Composable
// internal fun HealthCategoriesListItemLoadedPreview() {
//  MgoTheme {
//    HealthCategoriesListItemContent(
//      icon = HealthCategoryR.drawable.ic_medication,
//      title = CopyR.string.hc_medication_heading,
//      iconColor = MaterialTheme.colorScheme.supportContacts(),
//      listItemState = HealthCategoriesListItemState.LOADED,
//    )
//  }
// }
