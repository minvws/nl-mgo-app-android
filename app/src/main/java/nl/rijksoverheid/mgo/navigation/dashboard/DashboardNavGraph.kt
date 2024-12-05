package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.MainActivity
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen

fun NavGraphBuilder.addDashboardNavGraph(
    mainActivity: MainActivity,
    rootNavController: NavController,
) {
    navigation<DashboardNavigation.Root>(DashboardNavigation.BottomBar) {
        composable<DashboardNavigation.BottomBar> {
            DashboardBottomBarScreen(
                overviewStartDestination = DashboardNavigation.Overview.Root,
                overviewNavGraph = { navController ->
                    addDashboardOverviewNavGraph(rootNavController = rootNavController, navController = navController)
                },
                organizationsStartDestination = DashboardNavigation.Organizations.Root,
                organizationsNavGraph = { navController ->
                    addDashboardOrganizationsNavGraph(rootNavController = rootNavController, navController = navController)
                },
                settingsStartDestination = DashboardNavigation.Settings.Root,
                settingsNavGraph = {
                    addDashboardSettingsNavGraph(mainActivity = mainActivity, rootNavController = rootNavController)
                },
            )
        }
    }
}
