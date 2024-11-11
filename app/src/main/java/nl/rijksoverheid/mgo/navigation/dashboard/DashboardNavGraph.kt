package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen

fun NavGraphBuilder.addDashboardNavGraph(rootNavController: NavController) {
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
                aboutThisAppStartDestination = DashboardNavigation.AboutThisApp.Root,
                aboutThisAppNavGraph = { navController ->
                    composable<DashboardNavigation.AboutThisApp.Root> {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Red),
                        )
                    }
                },
            )
        }
    }
}
