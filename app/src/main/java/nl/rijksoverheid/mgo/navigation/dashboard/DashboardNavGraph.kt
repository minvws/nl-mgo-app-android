package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.healthcareprovider.HealthCareProviderNavigation

fun NavGraphBuilder.addDashboardNavGraph(
    rootNavController: NavHostController,
    healthCareProviderNavController: NavHostController,
) {
    navigation(
        startDestination = DashboardNavigationScreen.BottomBar.getRoute(),
        route = DashboardNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = DashboardNavigationScreen.BottomBar.getRoute()) {
            DashboardBottomBarScreen(
                overviewTab = {
                    HealthCareProviderNavigation(rootNavController = rootNavController, navController = healthCareProviderNavController)
                },
                aboutThisAppTab = {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(Color.Blue),
                    )
                },
            )
        }
    }
}
