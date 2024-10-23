package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconColor)
            Text(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                text = stringResource(id = title),
                style =
                    MaterialTheme.typography
                        .bodySmall,
            )
            when (listItemState) {
                HealthCategoriesListItemState.LOADING -> {
                    Text(
                        text = stringResource(id = CopyR.string.common_loading_data),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colors.contentTertiary(),
                    )
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .size(24.dp)
                                .padding(start = 8.dp),
                        color = MaterialTheme.colors.iconsSecondary(),
                        strokeWidth = 2.dp,
                    )
                }

                HealthCategoriesListItemState.NO_DATA -> {
                    Text(
                        text = stringResource(id = CopyR.string.common_no_data),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colors.contentTertiary(),
                    )
                }

                HealthCategoriesListItemState.LOADED -> {}
            }
        }
        if (hasDivider) {
            Divider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(0.33.dp)
                        .padding(start = 16.dp),
                color = MaterialTheme.colors.strokesPrimary(),
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
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
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
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
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
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            listItemState = HealthCategoriesListItemState.LOADED,
        )
    }
}
