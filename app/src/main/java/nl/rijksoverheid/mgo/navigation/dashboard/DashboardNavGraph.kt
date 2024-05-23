package nl.rijksoverheid.mgo.navigation.dashboard

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
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.healthcareprovider.HealthCareProviderNavigationScreen
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen

fun NavGraphBuilder.addDashboardNavGraph(navController: NavController) {
    navigation(
        startDestination = DashboardNavigationScreen.BottomBar.getRoute(),
        route = DashboardNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = DashboardNavigationScreen.BottomBar.getRoute()) {
            DashboardBottomBarScreen(
                overviewScreen = {
                    OverviewScreen(
                        onNavigateToLocalisation = {
                            navController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                        },
                        onNavigateToHealthCareProvider = {
                            navController.navigate(HealthCareProviderNavigationScreen.Start.getNavigationRoute())
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
