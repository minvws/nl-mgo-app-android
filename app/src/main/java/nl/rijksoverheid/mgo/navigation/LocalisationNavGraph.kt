package nl.rijksoverheid.mgo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.search.SearchScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addLocalisationNavGraph(navController: NavController) {
    navigation(
        startDestination = LocalisationNavigationScreen.Search.getRoute(),
        route = LocalisationNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = LocalisationNavigationScreen.Search.getRoute()) {
            SearchScreen(
                onNavigateToSearchResults = { name, city ->
                },
            )
        }
    }
}
