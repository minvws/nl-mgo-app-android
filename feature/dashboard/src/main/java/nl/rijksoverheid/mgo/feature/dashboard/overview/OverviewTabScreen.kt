package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.navigation.DashboardNavigationManager
import nl.rijksoverheid.mgo.feature.dashboard.navigation.DashboardNavigationScreen
import nl.rijksoverheid.mgo.feature.dashboard.navigation.ProvideDashboardNavigationManager
import nl.rijksoverheid.mgo.feature.dashboard.overview.detail.DetailScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

@Composable
internal fun OverviewTabScreen(
    navController: NavHostController,
    onNavigateToLocalisation: () -> Unit,
) {
    ProvideDashboardNavigationManager(navigationManager = DashboardNavigationManager(navController = navController)) {
        NavHost(
            navController = navController,
            startDestination = DashboardNavigationScreen.Start.getRoute(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            composableWithDefaultScreenTransitions(
                route = DashboardNavigationScreen.Start.getRoute(),
            ) {
                OverviewScreen(onNavigateToLocalisation = onNavigateToLocalisation)
            }

            composableWithDefaultScreenTransitions(
                route = DashboardNavigationScreen.Detail.getRoute(),
            ) {
                DetailScreen()
            }
        }
    }
}
