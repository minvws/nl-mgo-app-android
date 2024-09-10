package nl.rijksoverheid.mgo.feature.dashboard.overview.listItem

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import nl.rijksoverheid.mgo.feature.overview.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun OverviewListItem(
    @DrawableRes icon: Int,
    @ColorRes iconColor: Color,
    @StringRes title: Int,
    category: HealthCareCategory,
    onClickWhenLoaded: () -> Unit,
    modifier: Modifier = Modifier,
    hasDivider: Boolean = true,
) {
    if (LocalInspectionMode.current) {
        OverviewListItemContent(
            modifier = modifier,
            icon = icon,
            title = title,
            iconColor = iconColor,
            listItemState = OverviewListItemState.LOADED,
            hasDivider = hasDivider,
        )
    } else {
        val viewModel =
            hiltViewModel<OverviewListItemViewModel, OverviewListItemViewModel.Factory>(
                creationCallback = { factory -> factory.create(category) },
                key = category.toString(),
            )
        val listItemState by viewModel.listItemState.collectAsState()
        OverviewListItemContent(
            modifier = modifier.clickable(enabled = listItemState == OverviewListItemState.LOADED) { onClickWhenLoaded() },
            icon = icon,
            title = title,
            iconColor = iconColor,
            listItemState = listItemState,
            hasDivider = hasDivider,
        )
    }
}

@Composable
internal fun OverviewListItemContent(
    @DrawableRes icon: Int,
    @ColorRes iconColor: Color,
    @StringRes title: Int,
    listItemState: OverviewListItemState,
    modifier: Modifier = Modifier,
    hasDivider: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconColor)
            Text(modifier = Modifier.padding(start = 16.dp), text = stringResource(id = title), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            when (listItemState) {
                OverviewListItemState.LOADING -> {
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

                OverviewListItemState.NO_DATA -> {
                    Text(
                        text = stringResource(id = CopyR.string.common_no_data),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colors.contentTertiary(),
                    )
                }

                OverviewListItemState.LOADED -> {}
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
internal fun OverviewListItemLoadingPreview() {
    MgoTheme {
        OverviewListItemContent(
            icon = R.drawable.ic_medication,
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            listItemState = OverviewListItemState.LOADING,
        )
    }
}

@PreviewLightDark
@Composable
internal fun OverviewListItemNoDataPreview() {
    MgoTheme {
        OverviewListItemContent(
            icon = R.drawable.ic_medication,
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            listItemState = OverviewListItemState.NO_DATA,
        )
    }
}

@PreviewLightDark
@Composable
internal fun OverviewListItemLoadedPreview() {
    MgoTheme {
        OverviewListItemContent(
            icon = R.drawable.ic_medication,
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            listItemState = OverviewListItemState.LOADED,
        )
    }
}
