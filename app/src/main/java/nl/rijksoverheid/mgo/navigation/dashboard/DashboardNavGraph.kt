package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.BottomBarNavigation
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.navigation.newComposableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.overview.OverviewNavigation
import nl.rijksoverheid.mgo.navigation.organizations.OrganizationsNavigation
import nl.rijksoverheid.mgo.navigation.overview.addOverviewNavGraph

fun NavGraphBuilder.addDashboardNavGraph(
    rootNavController: NavController,
) {
    navigation<DashboardNavigation.Root>(DashboardNavigation.BottomBar) {
        composable<DashboardNavigation.BottomBar> {
            val dashboardNavController = rememberNavController()
            DashboardBottomBarScreen(
                navController = dashboardNavController,
                graph = {
                    composable<BottomBarNavigation.Overview> {
                        HealthCategoriesScreen(
                            appBarTitle = stringResource(R.string.overview_heading),
                            onNavigateToLocalisation = {
                                rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                            },
                            onNavigateToHealthCategory = { category, _ ->
                                dashboardNavController.navigate(
                                    OverviewNavigation.HealthCategory(HealthCategoryScreenArguments(category = category, filterOrganization = null)),
                                )
                            },
                            onNavigateRemoveOrganization = { },
                        )
                    }

                    composable<BottomBarNavigation.Organizations> {
                        Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
                            Button({ dashboardNavController.navigate(BottomBarNavigation.Test) }) { Text("Click me") }
                        }
                    }

                    composable<BottomBarNavigation.Test> {
                        Box(modifier = Modifier.fillMaxSize().background(Color.Blue))
                    }

                    newComposableWithDefaultScreenTransitions<OverviewNavigation.HealthCategory> { backStackEntry ->
                        val route = backStackEntry.toRoute<OverviewNavigation.HealthCategory>()
                        HealthCategoryScreen(
                            arguments = route.arguments,
                            onClickUiSchema = { toolbarTitle, uiSchema ->
                                //navController.navigate(OverviewNavigation.UiSchemaDetail(toolbarTitle = toolbarTitle, uiSchema =
                                              // uiSchema))
                            },
                            onNavigateBack = {
                                dashboardNavController.popBackStack()
                            },
                        )
                    }
                },
                overviewTab = { navController ->
                    NavHost(
                        navController = navController,
                        startDestination = OverviewNavigation.HealthCategories,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        addOverviewNavGraph(rootNavController = rootNavController, navController = navController)
                    }
                },
                organizationsTab = { navController ->
                    OrganizationsScreen(
                        onNavigateToLocalisation = {},
                        onNavigateToHealthCategories = {}
                    )
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
