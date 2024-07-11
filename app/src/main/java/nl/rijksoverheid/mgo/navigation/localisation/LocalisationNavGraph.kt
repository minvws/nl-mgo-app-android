package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreen
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreenViewModel
import nl.rijksoverheid.mgo.feature.localisation.searchresults.SearchResultsScreen
import nl.rijksoverheid.mgo.feature.localisation.stored.StoredHealthCareProvidersScreen
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
                onNavigateToSearchResults = { name, city ->
                    navController.navigate(LocalisationNavigationScreen.SearchResults.setName(name).setCity(city).getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.SearchResults.getRoute()) { backStackEntry ->
            val addOrganizationScreenViewModel =
                navController.getViewModel<AddOrganizationScreenViewModel>(
                    route = LocalisationNavigationScreen.AddOrganization.getRoute(),
                )
            SearchResultsScreen(
                name = LocalisationNavigationScreen.SearchResults.getName(backStackEntry),
                city = LocalisationNavigationScreen.SearchResults.getCity(backStackEntry),
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
            StoredHealthCareProvidersScreen(
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
