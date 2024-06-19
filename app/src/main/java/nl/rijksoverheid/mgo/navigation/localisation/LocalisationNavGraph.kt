package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.search.SearchScreen
import nl.rijksoverheid.mgo.feature.localisation.search.SearchScreenViewModel
import nl.rijksoverheid.mgo.feature.localisation.searchresults.SearchResultsScreen
import nl.rijksoverheid.mgo.feature.localisation.stored.StoredHealthCareProvidersScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.getViewModel

fun NavGraphBuilder.addLocalisationNavGraph(navController: NavController) {
    navigation(
        startDestination = LocalisationNavigationScreen.Search.getRoute(),
        route = LocalisationNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.Search.getRoute()) { backStackEntry ->
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSearchResults = { name, city ->
                    navController.navigate(LocalisationNavigationScreen.SearchResults.setName(name).setCity(city).getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.SearchResults.getRoute()) { backStackEntry ->
            val searchScreenViewModel =
                navController.getViewModel<SearchScreenViewModel>(
                    route = LocalisationNavigationScreen.Search.getRoute(),
                )
            SearchResultsScreen(
                name = LocalisationNavigationScreen.SearchResults.getName(backStackEntry),
                city = LocalisationNavigationScreen.SearchResults.getCity(backStackEntry),
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSearch = {
                    searchScreenViewModel?.setName("")
                    searchScreenViewModel?.setCity("")
                    navController.popBackStack(route = LocalisationNavigationScreen.Search.getNavigationRoute(), inclusive = false)
                },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = LocalisationNavigationScreen.Search.getNavigationRoute(),
                        inclusive = true,
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.Stored.getRoute()) {
            val searchScreenViewModel =
                navController.getViewModel<SearchScreenViewModel>(
                    route = LocalisationNavigationScreen.Search.getRoute(),
                )
            StoredHealthCareProvidersScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSearch = {
                    searchScreenViewModel?.setName("")
                    searchScreenViewModel?.setCity("")
                    navController.popBackStack(route = LocalisationNavigationScreen.Search.getNavigationRoute(), inclusive = false)
                },
                onLocalisationFinished = {
                    navController.popBackStack(
                        route = LocalisationNavigationScreen.Search.getNavigationRoute(),
                        inclusive = true,
                    )
                },
            )
        }
    }
}
