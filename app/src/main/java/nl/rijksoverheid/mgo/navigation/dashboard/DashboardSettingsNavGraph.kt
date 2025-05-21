package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.MainViewModel
import nl.rijksoverheid.mgo.feature.settings.about.accessibility.SettingsAboutAccessibilityScreen
import nl.rijksoverheid.mgo.feature.settings.about.home.SettingsAboutHomeScreen
import nl.rijksoverheid.mgo.feature.settings.about.opensource.SettingsAboutOpenSourceScreen
import nl.rijksoverheid.mgo.feature.settings.about.safety.SettingsAboutSafetyScreen
import nl.rijksoverheid.mgo.feature.settings.advanced.SettingsAdvancedScreen
import nl.rijksoverheid.mgo.feature.settings.display.SettingsDisplayScreen
import nl.rijksoverheid.mgo.feature.settings.home.SettingsHomeScreen
import nl.rijksoverheid.mgo.feature.settings.security.SettingsSecurityScreen
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation

/**
 * Adds all the navigation destinations that can be found in the settings bottom bar tab in the dashboard.
 *
 * @param rootNavController The top level nav controller.
 * @param navController The nav controller used in this navigation.
 */
internal fun NavGraphBuilder.addDashboardSettingsNavGraph(
  rootNavController: NavController,
  navController: NavController,
  mainViewModel: MainViewModel,
) {
  navigation<DashboardNavigation.Settings.Root>(DashboardNavigation.Settings.Home) {
    mgoComposable<DashboardNavigation.Settings.Home>(animate = false) {
      SettingsHomeScreen(
        onNavigateToDisplaySettings = {
          navController.navigate(DashboardNavigation.Settings.Display)
        },
        onNavigateToSecuritySettings = {
          navController.navigate(DashboardNavigation.Settings.Security)
        },
        onNavigateToAdvancedSettings = {
          navController.navigate(DashboardNavigation.Settings.Advanced)
        },
        onNavigateToAboutThisAppSettings = {
          navController.navigate(DashboardNavigation.Settings.About.Home)
        },
        onNavigateToOnboarding = {
          rootNavController.navigate(OnboardingNavigation.Root) {
            popUpTo(rootNavController.graph.id) {
              inclusive = true
            }
          }
        },
      )
    }

    mgoComposable<DashboardNavigation.Settings.Display> {
      SettingsDisplayScreen(
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Settings.Security> {
      SettingsSecurityScreen(
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Settings.Advanced> {
      SettingsAdvancedScreen(
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Settings.About.Home> {
      SettingsAboutHomeScreen(
        onNavigateToSecureUse = {
          navController.navigate(DashboardNavigation.Settings.About.Safety)
        },
        onNavigateToOpenSource = {
          navController.navigate(DashboardNavigation.Settings.About.OpenSource)
        },
        onNavigateToAccessibility = {
          navController.navigate(DashboardNavigation.Settings.About.Accessibility)
        },
        onNavigateBack = { navController.popBackStack() },
      )
    }

    mgoComposable<DashboardNavigation.Settings.About.Safety> {
      SettingsAboutSafetyScreen(
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Settings.About.OpenSource> {
      SettingsAboutOpenSourceScreen(
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Settings.About.Accessibility> {
      SettingsAboutAccessibilityScreen(
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }
  }
}
