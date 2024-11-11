package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.navigation.newComposableWithDefaultScreenTransitions

fun NavGraphBuilder.addDashboardNavGraph(rootNavController: NavController) {
    navigation<DashboardNavigation.Root>(DashboardNavigation.BottomBar) {
        composable<DashboardNavigation.BottomBar> {
            DashboardBottomBarScreen(
                overviewStartDestination = DashboardNavigation.Overview.Root,
                overviewNavGraph = { navController ->
                    composable<DashboardNavigation.Overview.Root> {
                        HealthCategoriesScreen(
                            appBarTitle = stringResource(R.string.overview_heading),
                            onNavigateToLocalisation = {
                                rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                            },
                            onNavigateToHealthCategory = { category, _ ->
                                navController.navigate(
                                    DashboardNavigation.Overview.HealthCareCategory(category = category),
                                )
                            },
                            onNavigateRemoveOrganization = { },
                        )
                    }

                    newComposableWithDefaultScreenTransitions<DashboardNavigation.Overview.HealthCareCategory> { backStackEntry ->
                        val route = backStackEntry.toRoute<DashboardNavigation.Overview.HealthCareCategory>()
                        HealthCategoryScreen(
                            category = route.category,
                            onClickUiSchema = { toolbarTitle, uiSchema ->
                                navController.navigate(
                                    DashboardNavigation.Overview.UISchemaDetail(
                                        toolbarTitle = toolbarTitle,
                                        uiSchema = uiSchema,
                                    ),
                                )
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                        )
                    }

                    newComposableWithDefaultScreenTransitions<DashboardNavigation.Overview.UISchemaDetail> { backStackEntry ->
                        val route = backStackEntry.toRoute<DashboardNavigation.Overview.UISchemaDetail>()
                        UiSchemaDetailScreen(
                            toolbarTitle = route.toolbarTitle,
                            uiSchema = route.uiSchema,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                        )
                    }
                },
                organizationsStartDestination = DashboardNavigation.Organizations.Root,
                organizationsNavGraph = { navController ->
                    composable<DashboardNavigation.Organizations.Root> {
                        OrganizationsScreen(
                            onNavigateToHealthCategories = { organization ->
                                navController.navigate(DashboardNavigation.Organizations.HealthCareCategories(organization))
                            },
                            onNavigateToLocalisation = {
                                rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                            },
                        )
                    }

                    newComposableWithDefaultScreenTransitions<DashboardNavigation.Organizations.HealthCareCategories> { backStackEntry ->
                        val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategories>()
                        HealthCategoriesScreen(
                            appBarTitle = route.organization.name,
                            organization = route.organization,
                            onNavigateToLocalisation = {
                                rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                            },
                            onNavigateToHealthCategory = { category, filterOrganization ->
                                navController.navigate(
                                    DashboardNavigation.Organizations.HealthCareCategory(
                                        category = category,
                                        filterOrganization = filterOrganization!!,
                                    ),
                                )
                            },
                            onNavigateRemoveOrganization = { },
                            onNavigateBack = { navController.popBackStack() },
                        )
                    }

                    newComposableWithDefaultScreenTransitions<DashboardNavigation.Organizations.HealthCareCategory> { backStackEntry ->
                        val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategory>()
                        HealthCategoryScreen(
                            category = route.category,
                            filterOrganization = route.filterOrganization,
                            onClickUiSchema = { toolbarTitle, uiSchema ->
                                navController.navigate(
                                    DashboardNavigation.Organizations.UISchemaDetail(
                                        toolbarTitle = toolbarTitle,
                                        uiSchema = uiSchema,
                                    ),
                                )
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                        )
                    }

                    newComposableWithDefaultScreenTransitions<DashboardNavigation.Organizations.UISchemaDetail> { backStackEntry ->
                        val route = backStackEntry.toRoute<DashboardNavigation.Organizations.UISchemaDetail>()
                        UiSchemaDetailScreen(
                            toolbarTitle = route.toolbarTitle,
                            uiSchema = route.uiSchema,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                        )
                    }
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
