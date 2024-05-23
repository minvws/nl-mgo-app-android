package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.healthcareprovider.details.HealthCareProviderDetailsScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addHealthCareProviderNavGraph(navController: NavController) {
    navigation(
        startDestination = HealthCareProviderNavigationScreen.Details.getRoute(),
        route = HealthCareProviderNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = HealthCareProviderNavigationScreen.Details.getRoute()) { backStackEntry ->
            HealthCareProviderDetailsScreen(
                providerName = HealthCareProviderNavigationScreen.Details.getProviderName(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
