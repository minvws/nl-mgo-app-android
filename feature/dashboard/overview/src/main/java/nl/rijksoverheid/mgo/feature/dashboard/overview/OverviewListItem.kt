package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.feature.overview.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

enum class OverviewListItemState {
    LOADING,
    LOADED,
    NO_DATA,
}

@Composable
internal fun OverviewListItem(
    @DrawableRes icon: Int,
    @ColorRes iconColor: Color,
    @StringRes title: Int,
    state: OverviewListItemState,
    modifier: Modifier = Modifier,
    hasDivider: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconColor)
            Text(modifier = Modifier.padding(start = 16.dp), text = stringResource(id = title), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            when (state) {
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
        OverviewListItem(
            icon = R.drawable.ic_medication,
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            state = OverviewListItemState.LOADING,
        )
    }
}

@PreviewLightDark
@Composable
internal fun OverviewListItemNoDataPreview() {
    MgoTheme {
        OverviewListItem(
            icon = R.drawable.ic_medication,
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            state = OverviewListItemState.NO_DATA,
        )
    }
}

@PreviewLightDark
@Composable
internal fun OverviewListItemLoadedPreview() {
    MgoTheme {
        OverviewListItem(
            icon = R.drawable.ic_medication,
            title = CopyR.string.health_category_medication,
            iconColor = MaterialTheme.colors.supportHuisarts(),
            state = OverviewListItemState.LOADED,
        )
    }
}
