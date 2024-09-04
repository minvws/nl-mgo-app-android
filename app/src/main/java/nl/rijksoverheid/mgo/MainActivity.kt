package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.devicerooted.DeviceRootedDialog
import nl.rijksoverheid.mgo.feature.config.UpdateRequiredScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.config.ConfigNavigationScreen
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen
import nl.rijksoverheid.mgo.navigation.dashboard.addDashboardNavGraph
import nl.rijksoverheid.mgo.navigation.localisation.addLocalisationNavGraph
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigationScreen
import nl.rijksoverheid.mgo.navigation.onboarding.addOnboardingNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MgoTheme(modifier = Modifier.fillMaxSize()) {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination =
                    if (viewModel.hasSeenOnboarding()) {
                        DashboardNavigationScreen.Start.getNavigationRoute()
                    } else {
                        OnboardingNavigationScreen.Start.getNavigationRoute()
                    }
                val organizationNavController = rememberNavController()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                ) {
                    addOnboardingNavGraph(navController = navController)
                    addDashboardNavGraph(
                        rootNavController = navController,
                        organizationNavController = organizationNavController,
                    )
                    addLocalisationNavGraph(navController = navController)
                    composableWithDefaultScreenTransitions(route = ConfigNavigationScreen.UpdateRequired.getRoute()) {
                        UpdateRequiredScreen()
                    }
                }

                // Refresh config on every app resume. The backend handles caching.
                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    viewModel.refreshConfig()
                }

                // Handle config state changes
                val configState by viewModel.configStateFlow.collectAsStateWithLifecycle()
                when (configState) {
                    ConfigState.NoAction -> {}
                    ConfigState.UpdateRequired -> navController.navigate(ConfigNavigationScreen.UpdateRequired.getNavigationRoute())
                }

                // Device rooted dialog
                DeviceRootedDialog(show = viewModel.showDeviceRootedDialog())
            }
        }
    }
}
