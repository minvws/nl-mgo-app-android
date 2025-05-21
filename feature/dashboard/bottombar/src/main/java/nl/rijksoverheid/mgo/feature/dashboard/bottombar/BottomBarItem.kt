package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.feature.bottombar.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Represents a bottom bar item in the dashboard.
 *
 * @param route The root navigation of the bottom bar item.
 * @param titleId The string resource id of the title to show in the bottom bar item.
 * @param deselectedIconId The drawable resource id of the not selected icon in the bottom bar item.
 * @param selectedIconId The drawable resource id of the selected icon in the bottom bar item.
 */
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
  SETTINGS(
    BottomBarItemNavigation.Settings,
    CopyR.string.bottombar_settings,
    R.drawable.ic_bottombar_item_settings_deselected,
    R.drawable.ic_bottombar_item_settings_selected,
  ),
}
