package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.supportContacts
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object HealthCategoriesListItemTestTag {
  const val LIST_ITEM = "HealthCategoriesListItem"
}

/**
 * Composable that shows a list item representing a health care category.
 * The list item has it's own viewmodel that listens to the health care data
 * for that category.
 *
 * @param icon The icon to show in the list item.
 * @param iconColor The color of the icon to show in the list item.
 * @param title The title to show in the list item.
 * @Param filterOrganization If not null, will only show only health care data for this organization. If null will show for all added
 * organizations.
 * @param category The [HealthCareCategory] for this list item.
 * @param modifier The modifier to be applied.
 * @param hasDivider If the list item has a divider.
 */
@Composable
internal fun HealthCategoriesListItem(
  @DrawableRes icon: Int,
  @ColorRes iconColor: Color,
  @StringRes title: Int,
  filterOrganization: MgoOrganization?,
  category: HealthCareCategory,
  modifier: Modifier = Modifier,
  hasDivider: Boolean = true,
) {
  if (LocalInspectionMode.current) {
    HealthCategoriesListItemContent(
      modifier = modifier,
      icon = icon,
      title = title,
      iconColor = iconColor,
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
      icon = icon,
      title = title,
      iconColor = iconColor,
      listItemState = listItemState,
      hasDivider = hasDivider,
    )
  }
}

@Composable
internal fun HealthCategoriesListItemContent(
  @DrawableRes icon: Int,
  @ColorRes iconColor: Color,
  @StringRes title: Int,
  listItemState: HealthCategoriesListItemState,
  modifier: Modifier = Modifier,
  hasDivider: Boolean = true,
) {
  Column(modifier = modifier.fillMaxWidth().testTag(HealthCategoriesListItemTestTag.LIST_ITEM)) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
      Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconColor)
      Text(
        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
        text = stringResource(id = title),
        style =
          MaterialTheme.typography
            .bodyMedium,
      )
      when (listItemState) {
        HealthCategoriesListItemState.LOADING -> {
          Text(
            text = stringResource(id = CopyR.string.common_loading_data),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.contentSecondary(),
          )
          CircularProgressIndicator(
            modifier =
              Modifier
                .size(24.dp)
                .padding(start = 8.dp),
            color = MaterialTheme.colorScheme.symbolsSecondary(),
            strokeWidth = 2.dp,
          )
        }

        HealthCategoriesListItemState.NO_DATA -> {
          Text(
            text = stringResource(id = CopyR.string.common_no_data),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.contentSecondary(),
          )
        }

        HealthCategoriesListItemState.LOADED -> {}
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

@PreviewLightDark
@Composable
internal fun HealthCategoriesListItemLoadingPreview() {
  MgoTheme {
    HealthCategoriesListItemContent(
      icon = R.drawable.ic_medication,
      title = CopyR.string.hc_medication_heading,
      iconColor = MaterialTheme.colorScheme.supportContacts(),
      listItemState = HealthCategoriesListItemState.LOADING,
    )
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesListItemNoDataPreview() {
  MgoTheme {
    HealthCategoriesListItemContent(
      icon = R.drawable.ic_medication,
      title = CopyR.string.hc_medication_heading,
      iconColor = MaterialTheme.colorScheme.supportContacts(),
      listItemState = HealthCategoriesListItemState.NO_DATA,
    )
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesListItemLoadedPreview() {
  MgoTheme {
    HealthCategoriesListItemContent(
      icon = R.drawable.ic_medication,
      title = CopyR.string.hc_medication_heading,
      iconColor = MaterialTheme.colorScheme.supportContacts(),
      listItemState = HealthCategoriesListItemState.LOADED,
    )
  }
}
