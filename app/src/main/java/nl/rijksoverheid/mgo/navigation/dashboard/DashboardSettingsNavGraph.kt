package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.settings.SettingsScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation

fun NavGraphBuilder.addDashboardSettingsNavGraph(rootNavController: NavController) {
    navigation<DashboardNavigation.Settings.Root>(DashboardNavigation.Settings.Debug) {
        mgoComposable<DashboardNavigation.Settings.Debug>(animate = false) {
            SettingsScreen(
                onNavigateToOnboarding = {
                    rootNavController.navigate(OnboardingNavigation.Root) {
                        popUpTo(rootNavController.graph.id) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
