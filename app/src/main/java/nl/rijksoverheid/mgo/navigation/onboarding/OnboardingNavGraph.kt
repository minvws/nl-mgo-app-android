package nl.rijksoverheid.mgo.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.onboarding.introduction.IntroductionScreen
import nl.rijksoverheid.mgo.feature.onboarding.proposition.PropositionOverviewScreen
import nl.rijksoverheid.mgo.navigation.newComposableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeNavigationScreen

fun NavGraphBuilder.addOnboardingNavGraph(navController: NavController) {
    navigation<OnboardingNavigation.Root>(OnboardingNavigation.Introduction) {
        newComposableWithDefaultScreenTransitions<OnboardingNavigation.Introduction> {
            IntroductionScreen(
                onNavigateToProposition = {
                    navController.navigate(OnboardingNavigation.Proposition)
                },
            )
        }

        newComposableWithDefaultScreenTransitions<OnboardingNavigation.Proposition> {
            PropositionOverviewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOnboardingFinished = {
                    navController.navigate(PinCodeNavigationScreen.Start.getNavigationRoute())
                },
            )
        }
    }
}
