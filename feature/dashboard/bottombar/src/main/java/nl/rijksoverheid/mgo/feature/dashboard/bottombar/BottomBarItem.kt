package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.feature.bottombar.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal enum class BottomBarItem(
    val route: BottomBarItemNavigation,
    @StringRes val titleId: Int,
    @DrawableRes val deselectedIconId: Int,
    @DrawableRes val selectedIconId: Int,
) {
    OVERVIEW(
        BottomBarItemNavigation.Overview,
        CopyR.string.bottombar_overview,
        R.drawable.ic_bottombar_item_overview_deselected,
        R.drawable.ic_bottombar_item_overview_selected,
    ),
    ORGANIZATIONS(
        BottomBarItemNavigation.Organizations,
        CopyR.string.bottombar_healthcareproviders,
        R.drawable.ic_bottombar_item_organizations_deselected,
        R.drawable.ic_bottombar_item_organizations_selected,
    ),
    ABOUT_THIS_APP(
        BottomBarItemNavigation.AboutThisApp,
        CopyR.string.bottombar_about_this_app,
        R.drawable.ic_bottombar_item_about_this_app_deselected,
        R.drawable.ic_bottombar_item_about_this_app_selected,
    ),
}
