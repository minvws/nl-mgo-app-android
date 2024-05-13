package nl.rijksoverheid.mgo.feature.onboarding

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.feature.onboarding.navigation.OnboardingNavigationManager
import nl.rijksoverheid.mgo.feature.onboarding.navigation.OnboardingNavigationScreen
import nl.rijksoverheid.mgo.feature.onboarding.navigation.ProvideOnboardingNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

@Composable
fun OnboardingScreen() {
    val navController = rememberNavController()
    ProvideOnboardingNavigationManager(navigationManager = OnboardingNavigationManager(navController = navController)) {
        NavHost(
            navController = navController,
            startDestination = OnboardingNavigationScreen.Introduction.getRoute(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            composableWithDefaultScreenTransitions(
                route = OnboardingNavigationScreen.Introduction.getRoute(),
            ) {
                IntroductionScreen()
            }

            composableWithDefaultScreenTransitions(
                route = OnboardingNavigationScreen.PrivacyOverview.getRoute(),
            ) {
                PrivacyOverviewScreen()
            }
        }
    }
}
