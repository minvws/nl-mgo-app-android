package nl.rijksoverheid.mgo.navigation.organizations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.navigation.newComposableWithDefaultScreenTransitions

@Composable
fun OrganizationsNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = OrganizationsNavigationScreen.Start.getRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        //newComposableWithDefaultScreenTransitions<OrganizationsNavigationScreen.Start> {  }

//        composableWithDefaultScreenTransitions(DashboardNavigationScreen.HealthCategories.getRoute()) { backStackEntry ->
//            val organization = DashboardNavigationScreen.HealthCategories.getOrganization(backStackEntry)
//            HealthCategoriesScreen(
//                appBarTitle = organization.name,
//                organization = organization,
//                onNavigateBack = {
//                    navController.popBackStack()
//                },
//                onNavigateToLocalisation = {
//                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
//                },
//                onNavigateToHealthCategory = { category, organization ->
//                    navController.navigate(
//                        DashboardNavigationScreen.HealthCategory.setArguments(HealthCategoryScreenArguments(category, organization))
//                            .getNavigationRoute(),
//                    )
//                },
//                onNavigateRemoveOrganization = { organization ->
//                    navController.navigate(
//                        DashboardNavigationScreen.RemoveOrganization.setProviderId(
//                            organization.id,
//                        ).setProviderName(organization.name).getNavigationRoute(),
//                    )
//                },
//            )
//        }
//
//        composableWithDefaultScreenTransitions(DashboardNavigationScreen.HealthCategory.getRoute()) { backStackEntry ->
//            HealthCategoryScreen(
//                arguments = DashboardNavigationScreen.HealthCategory.getArguments(backStackEntry),
//                onClickUiSchema = { toolbarTitle, uiSchema ->
//                    navController.navigate(
//                        DashboardNavigationScreen.UiSchemaDetail.setToolbarTitle(toolbarTitle).setUiSchema(uiSchema)
//                            .getNavigationRoute(),
//                    )
//                },
//                onNavigateBack = {
//                    navController.popBackStack()
//                },
//            )
//        }
//
//        composableWithDefaultScreenTransitions(DashboardNavigationScreen.UiSchemaDetail.getRoute()) { backStackEntry ->
//            UiSchemaDetailScreen(
//                toolbarTitle = DashboardNavigationScreen.UiSchemaDetail.getToolbarTitle(backStackEntry),
//                uiSchema = DashboardNavigationScreen.UiSchemaDetail.getUiSchema(backStackEntry),
//                onNavigateBack = {
//                    navController.popBackStack()
//                },
//            )
//        }
//
//        dialogWithDefaultScreenTransitions(
//            route = DashboardNavigationScreen.RemoveOrganization.getRoute(),
//        ) { backStackEntry ->
//            RemoveOrganizationScreen(
//                providerId = DashboardNavigationScreen.RemoveOrganization.getProviderId(backStackEntry),
//                providerName = DashboardNavigationScreen.RemoveOrganization.getProviderName(backStackEntry),
//                onNavigateBack = { navController.popBackStack() },
//                onNavigateToDashboard = {
//                    navController.popBackStack(
//                        route = OrganizationsNavigationScreen.Start.getRoute(),
//                        inclusive = false,
//                    )
//                },
//            )
//        }
    }
}
