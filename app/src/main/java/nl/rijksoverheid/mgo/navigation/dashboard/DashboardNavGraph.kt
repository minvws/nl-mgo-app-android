package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.organization.OverviewNavigation
import nl.rijksoverheid.mgo.navigation.organizations.OrganizationsNavigation

fun NavGraphBuilder.addDashboardNavGraph(
    rootNavController: NavHostController,
    overviewNavController: NavHostController,
    organizationsNavController: NavHostController,
) {
    navigation(
        startDestination = DashboardNavigationScreen.BottomBar.getRoute(),
        route = DashboardNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = DashboardNavigationScreen.BottomBar.getRoute()) {
            DashboardBottomBarScreen(
                overviewTab = {
                    OverviewNavigation(rootNavController = rootNavController, navController = overviewNavController)
                },
                organizationsTab = {
                    OrganizationsNavigation(rootNavController = rootNavController, navController = organizationsNavController)
                },
                aboutThisAppTab = {
                    val applicationContext = LocalContext.current.applicationContext
                    Scaffold { innerPadding ->
                        Column(modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
                            Text(
                                modifier = Modifier.padding(top = 32.dp),
                                text = "Over de app",
                                style = MaterialTheme.typography.headingLarge,
                            )
                            Button(
                                modifier = Modifier.padding(top = 16.dp),
                                onClick = {
                                    val packageName = applicationContext.packageName
                                    val runtime = Runtime.getRuntime()
                                    runtime.exec("pm clear $packageName")
                                },
                                content = {
                                    Text(text = "Reset App")
                                },
                            )
                        }
                    }
                },
            )
        }
    }
}
