package nl.rijksoverheid.mgo.navigation.organization

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
import nl.rijksoverheid.mgo.feature.organization.medicationUse.MedicationUseScreen
import nl.rijksoverheid.mgo.feature.organization.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.feature.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dialogWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen

@Composable
fun OrganizationNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = OrganizationNavigationScreen.Overview.getRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.Overview.getRoute()) {
            OverviewScreen(
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToMedications = {
                    navController.navigate(
                        OrganizationNavigationScreen.MedicationUse
                            .getNavigationRoute(),
                    )
                },
            )
        }

        dialogWithDefaultScreenTransitions(
            route = OrganizationNavigationScreen.RemoveOrganization.getRoute(),
        ) { backStackEntry ->
            RemoveOrganizationScreen(
                providerId = OrganizationNavigationScreen.RemoveOrganization.getProviderId(backStackEntry),
                providerName = OrganizationNavigationScreen.RemoveOrganization.getProviderName(backStackEntry),
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = OrganizationNavigationScreen.Overview.getRoute(),
                        inclusive = false,
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.MedicationUse.getRoute()) {
            MedicationUseScreen(
                onClickUiSchema = { toolbarTitle, uiSchema ->
                    navController.navigate(
                        OrganizationNavigationScreen.UiSchemaDetail.setToolbarTitle(toolbarTitle).setUiSchema(uiSchema)
                            .getNavigationRoute(),
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.UiSchemaDetail.getRoute()) { backStackEntry ->
            UiSchemaDetailScreen(
                toolbarTitle = OrganizationNavigationScreen.UiSchemaDetail.getToolbarTitle(backStackEntry),
                uiSchema = OrganizationNavigationScreen.UiSchemaDetail.getUiSchema(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
