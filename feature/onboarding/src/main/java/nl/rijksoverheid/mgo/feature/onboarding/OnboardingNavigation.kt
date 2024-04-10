package nl.rijksoverheid.mgo.feature.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

fun NavGraphBuilder.addOnboardingNavigationGraph() {
    navigation(
        startDestination = NavigationScreen.Onboarding.Introduction.getRoute(),
        route = NavigationScreen.Onboarding.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Onboarding.Introduction.getRoute(),
        ) {
            IntroductionScreen()
        }

        composableWithDefaultScreenTransitions(
            route = NavigationScreen.Onboarding.PrivacyOverview.getRoute(),
        ) {
            PrivacyOverviewScreen()
        }
    }
}
