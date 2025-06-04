package nl.rijksoverheid.mgo.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.onboarding.introduction.IntroductionScreen
import nl.rijksoverheid.mgo.feature.onboarding.proposition.PropositionOverviewScreen
import nl.rijksoverheid.mgo.navigation.mgoComposableExt
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation

/**
 * Adds all the navigation destinations that can be found when going through the onboarding.
 * @param navController The nav controller used in this navigation.
 */
fun NavGraphBuilder.addOnboardingNavGraph(navController: NavController) {
  navigation<OnboardingNavigation.Root>(OnboardingNavigation.Introduction) {
    mgoComposableExt<OnboardingNavigation.Introduction> {
      IntroductionScreen(
        onNavigateToProposition = {
          navController.navigate(OnboardingNavigation.Proposition)
        },
      )
    }

    mgoComposableExt<OnboardingNavigation.Proposition> {
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
