package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                    val applicationContext = LocalContext.current.applicationContext
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            val packageName = applicationContext.packageName
                            val runtime = Runtime.getRuntime()
                            runtime.exec("pm clear $packageName")
                        },
                        content = {
                            Text(text = "Reset App")
                        },
                    )
                },
            )
        }
    }
}
