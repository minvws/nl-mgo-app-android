package nl.rijksoverheid.mgo.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.onboarding.introduction.IntroductionScreen
import nl.rijksoverheid.mgo.feature.onboarding.proposition.PropositionOverviewScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen

fun NavGraphBuilder.addOnboardingNavGraph(navController: NavController) {
    navigation(
        startDestination = OnboardingNavigationScreen.Introduction.getRoute(),
        route = OnboardingNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = OnboardingNavigationScreen.Introduction.getRoute()) {
            IntroductionScreen(
                onNavigateToProposition = {
                    navController.navigate(OnboardingNavigationScreen.Proposition.getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(route = OnboardingNavigationScreen.Proposition.getRoute()) {
            PropositionOverviewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOnboardingFinished = {
                    navController.navigate(DashboardNavigationScreen.Start.getNavigationRoute()) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
