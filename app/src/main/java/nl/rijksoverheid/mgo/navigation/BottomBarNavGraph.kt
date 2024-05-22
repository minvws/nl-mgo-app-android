package nl.rijksoverheid.mgo.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addBottomBarNavGraph(navController: NavController) {
    navigation(
        startDestination = BottomBarNavigationScreen.BottomBar.getRoute(),
        route = BottomBarNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = BottomBarNavigationScreen.BottomBar.getRoute()) {
            DashboardBottomBarScreen(
                overviewScreen = {
                    OverviewScreen(
                        onNavigateToLocalisation = {
                            navController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                        },
                    )
                },
                aboutThisAppScreen = {
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
