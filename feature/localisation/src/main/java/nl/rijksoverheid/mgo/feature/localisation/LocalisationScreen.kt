package nl.rijksoverheid.mgo.feature.localisation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.feature.localisation.navigation.LocalisationNavigationManager
import nl.rijksoverheid.mgo.feature.localisation.navigation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.feature.localisation.navigation.ProvideLocalisationNavigationManager
import nl.rijksoverheid.mgo.feature.localisation.search.SearchScreen
import nl.rijksoverheid.mgo.feature.localisation.searchresults.SearchResultsScreen
import nl.rijksoverheid.mgo.feature.localisation.stored.StoredHealthCareProvidersScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

@Composable
fun LocalisationScreen() {
    val navController = rememberNavController()
    ProvideLocalisationNavigationManager(navigationManager = LocalisationNavigationManager(navController = navController)) {
        NavHost(
            navController = navController,
            startDestination = LocalisationNavigationScreen.Search.getRoute(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            composableWithDefaultScreenTransitions(
                route = LocalisationNavigationScreen.Search.getRoute(),
            ) {
                SearchScreen()
            }

            composableWithDefaultScreenTransitions(
                route = LocalisationNavigationScreen.SearchResults.getRoute(),
            ) {
                SearchResultsScreen()
            }

            composableWithDefaultScreenTransitions(
                route = LocalisationNavigationScreen.StoredHealthCareProviders.getRoute(),
            ) {
                StoredHealthCareProvidersScreen()
            }
        }
    }
}
