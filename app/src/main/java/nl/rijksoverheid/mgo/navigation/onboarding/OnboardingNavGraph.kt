package nl.rijksoverheid.mgo.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.onboarding.introduction.IntroductionScreen
import nl.rijksoverheid.mgo.feature.onboarding.proposition.PropositionOverviewScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation

fun NavGraphBuilder.addOnboardingNavGraph(navController: NavController) {
    navigation<OnboardingNavigation.Root>(OnboardingNavigation.Introduction) {
        mgoComposable<OnboardingNavigation.Introduction> {
            IntroductionScreen(
                onNavigateToProposition = {
                    navController.navigate(OnboardingNavigation.Proposition)
                },
            )
        }

        mgoComposable<OnboardingNavigation.Proposition> {
            PropositionOverviewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOnboardingFinished = {
                    navController.navigate(PinCodeCreateNavigation.Root)
                },
            )
        }
    }
}
