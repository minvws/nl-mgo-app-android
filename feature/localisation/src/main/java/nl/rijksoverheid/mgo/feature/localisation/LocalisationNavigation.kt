package nl.rijksoverheid.mgo.feature.localisation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.overview.AddedHealthCareOverviewScreen
import nl.rijksoverheid.mgo.feature.localisation.search.HealthCareSearchScreen
import nl.rijksoverheid.mgo.feature.localisation.searchresults.HealthCareSearchResultsScreen
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addLocalisationNavigationGraph() {
    navigation(
        startDestination = NavigationScreen.Localisation.Search.getRoute(),
        route = NavigationScreen.Localisation.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Localisation.Search.getRoute(),
        ) {
            HealthCareSearchScreen()
        }

        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Localisation.SearchResults.getRoute(),
        ) {
            HealthCareSearchResultsScreen()
        }

        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Localisation.Overview.getRoute(),
        ) {
            AddedHealthCareOverviewScreen()
        }
    }
}
