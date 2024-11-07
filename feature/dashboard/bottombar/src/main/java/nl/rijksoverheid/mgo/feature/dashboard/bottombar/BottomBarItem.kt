package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.feature.bottombar.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

enum class BottomBarItem(
    val route: Any,
    @StringRes val titleId: Int,
    @DrawableRes val deselectedIconId: Int,
    @DrawableRes val selectedIconId: Int,
) {
    OVERVIEW(
        BottomBarNavigation.Overview,
        CopyR.string.bottombar_overview,
        R.drawable.ic_bottombar_item_overview_deselected,
        R.drawable.ic_bottombar_item_overview_selected,
    ),
    ORGANIZATIONS(
        BottomBarNavigation.Organizations,
        CopyR.string.bottombar_healthcareproviders,
        R.drawable.ic_bottombar_item_organizations_deselected,
        R.drawable.ic_bottombar_item_organizations_selected,
    ),
    ABOUT_THIS_APP(
        BottomBarNavigation.AboutThisApp,
        CopyR.string.bottombar_about_this_app,
        R.drawable.ic_bottombar_item_about_this_app_deselected,
        R.drawable.ic_bottombar_item_about_this_app_selected,
    );

    @Composable
    fun getNavController(): NavHostController {
        return rememberNavController()
    }
}
