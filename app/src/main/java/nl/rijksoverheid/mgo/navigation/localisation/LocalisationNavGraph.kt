package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreen
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreenViewModel
import nl.rijksoverheid.mgo.feature.localisation.organizationSearch.OrganizationSearchScreen
import nl.rijksoverheid.mgo.feature.localisation.organizationList.OrganizationListScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.getViewModel

fun NavGraphBuilder.addLocalisationNavGraph(navController: NavController) {
    navigation(
        startDestination = LocalisationNavigationScreen.AddOrganization.getRoute(),
        route = LocalisationNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.AddOrganization.getRoute()) {
            AddOrganizationScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToOrganizationSearch = { name, city ->
                    navController.navigate(LocalisationNavigationScreen.OrganizationList.setName(name).setCity(city).getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.OrganizationList.getRoute()) { backStackEntry ->
            val addOrganizationScreenViewModel =
                navController.getViewModel<AddOrganizationScreenViewModel>(
                    route = LocalisationNavigationScreen.AddOrganization.getRoute(),
                )
            OrganizationSearchScreen(
                name = LocalisationNavigationScreen.OrganizationList.getName(backStackEntry),
                city = LocalisationNavigationScreen.OrganizationList.getCity(backStackEntry),
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddOrganization = {
                    addOrganizationScreenViewModel?.setName("")
                    addOrganizationScreenViewModel?.setCity("")
                    navController.popBackStack(route = LocalisationNavigationScreen.AddOrganization.getNavigationRoute(), inclusive = false)
                },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = LocalisationNavigationScreen.AddOrganization.getNavigationRoute(),
                        inclusive = true,
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.Stored.getRoute()) {
            val addOrganizationScreenViewModel =
                navController.getViewModel<AddOrganizationScreenViewModel>(
                    route = LocalisationNavigationScreen.AddOrganization.getRoute(),
                )
            OrganizationListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddOrganization = {
                    addOrganizationScreenViewModel?.setName("")
                    addOrganizationScreenViewModel?.setCity("")
                    navController.popBackStack(route = LocalisationNavigationScreen.AddOrganization.getNavigationRoute(), inclusive = false)
                },
                onLocalisationFinished = {
                    navController.popBackStack(
                        route = LocalisationNavigationScreen.AddOrganization.getNavigationRoute(),
                        inclusive = true,
                    )
                },
            )
        }
    }
}
