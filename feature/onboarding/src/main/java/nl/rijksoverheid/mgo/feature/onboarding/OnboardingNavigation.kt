package nl.rijksoverheid.mgo.feature.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

fun NavGraphBuilder.addOnboardingNavigationGraph() {
    navigation(
        startDestination = NavigationScreen.Onboarding.Introduction.getRoute(),
        route = NavigationScreen.Onboarding.Start.getRoute(),
    ) {
        composable(NavigationScreen.Onboarding.Introduction.getRoute()) {
            IntroductionScreen()
        }
    }
}
