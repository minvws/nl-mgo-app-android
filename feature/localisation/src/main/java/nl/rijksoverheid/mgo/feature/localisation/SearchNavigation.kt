package nl.rijksoverheid.mgo.feature.localisation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.getsearchresults.GetSearchResultsScreen
import nl.rijksoverheid.mgo.feature.localisation.search.SearchScreen
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addAddHealthCareNavigationGraph() {
    navigation(
        startDestination = NavigationScreen.Localisation.Search.getRoute(),
        route = NavigationScreen.Localisation.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Localisation.Search.getRoute(),
        ) {
            SearchScreen()
        }

        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Localisation.SearchResults.getRoute(),
        ) {
            GetSearchResultsScreen()
        }
    }
}
