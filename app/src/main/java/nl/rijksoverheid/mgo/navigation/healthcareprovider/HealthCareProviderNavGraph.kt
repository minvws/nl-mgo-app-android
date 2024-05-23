package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addHealthCareProviderNavGraph(navController: NavController) {
    navigation(
        startDestination = HealthCareProviderNavigationScreen.Details.getRoute(),
        route = HealthCareProviderNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = HealthCareProviderNavigationScreen.Details.getRoute()) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Red))
        }
    }
}
