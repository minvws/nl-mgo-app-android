package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.feature.bottombar.R
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

data class BottomBarItem(
    val route: BottomBarItemNavigation,
    @StringRes val titleId: Int,
    @DrawableRes val deselectedIconId: Int,
    @DrawableRes val selectedIconId: Int,
)

sealed class BottomBarItemNavigation {
    @Serializable
    data object Overview : BottomBarItemNavigation()

    @Serializable
    data object Organizations : BottomBarItemNavigation()

    @Serializable
    data object AboutThisApp : BottomBarItemNavigation()
}

fun createOverviewBottomBarItem() =
    BottomBarItem(
        BottomBarItemNavigation.Overview,
        CopyR.string.bottombar_overview,
        R.drawable.ic_bottombar_item_overview_deselected,
        R.drawable.ic_bottombar_item_overview_selected,
    )

fun createOrganizationsBottomBarItem() =
    BottomBarItem(
        BottomBarItemNavigation.Organizations,
        CopyR.string.bottombar_overview,
        R.drawable.ic_bottombar_item_overview_deselected,
        R.drawable.ic_bottombar_item_overview_selected,
    )

fun createAboutThisAppBottomBarItem() =
    BottomBarItem(
        BottomBarItemNavigation.AboutThisApp,
        CopyR.string.bottombar_about_this_app,
        R.drawable.ic_bottombar_item_about_this_app_deselected,
        R.drawable.ic_bottombar_item_about_this_app_selected,
    )
