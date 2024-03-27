package nl.rijksoverheid.mgo.feature.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.defaultScreenEnterTransition
import nl.rijksoverheid.mgo.framework.navigation.defaultScreenExitTransition
import nl.rijksoverheid.mgo.framework.navigation.defaultScreenPopEnterTransition

fun NavGraphBuilder.addOnboardingNavigationGraph() {
    navigation(
        startDestination = NavigationScreen.Onboarding.Introduction.getRoute(),
        route = NavigationScreen.Onboarding.Start.getRoute(),
    ) {
        composable(
            route = NavigationScreen.Onboarding.Introduction.getRoute(),
            enterTransition = { defaultScreenEnterTransition() },
            exitTransition = { defaultScreenExitTransition() },
            popEnterTransition = { defaultScreenPopEnterTransition() },
        ) {
            IntroductionScreen()
        }

        composable(
            route = NavigationScreen.Onboarding.PrivacyOverview.getRoute(),
            enterTransition = { defaultScreenEnterTransition() },
            exitTransition = { defaultScreenExitTransition() },
            popEnterTransition = { defaultScreenPopEnterTransition() },
        ) {
            PrivacyOverviewScreen()
        }
    }
}
