package nl.rijksoverheid.mgo.feature.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

sealed class BottomBarItem(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
) {
    data object Overview : BottomBarItem(CopyR.string.dashboard_bottombar_item_overview, R.drawable.ic_bottombar_item_overview)

    data object AboutThisApp : BottomBarItem(
        CopyR.string.dashboard_bottombar_item_about_this_app,
        R.drawable
            .ic_bottombar_item_about_this_app,
    )
}
