package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.feature.config.UpdateRequiredScreen
import nl.rijksoverheid.mgo.feature.dashboard.DashboardScreen
import nl.rijksoverheid.mgo.feature.onboarding.addOnboardingNavigationGraph
import nl.rijksoverheid.mgo.framework.navigation.DefaultNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.ProvideNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.defaultScreenEnterTransition
import nl.rijksoverheid.mgo.framework.navigation.defaultScreenExitTransition
import nl.rijksoverheid.mgo.framework.navigation.defaultScreenPopEnterTransition

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MgoTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination =
                    if (viewModel.hasSeenOnboarding()) {
                        NavigationScreen.Dashboard.getRoute()
                    } else {
                        NavigationScreen
                            .Onboarding.Start.getRoute()
                    }
                val rootNavController = rememberNavController()
                ProvideNavigationManager(navigationManager = DefaultNavigationManager(navController = rootNavController)) {
                    NavHost(
                        navController = rootNavController,
                        startDestination = startDestination,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        addOnboardingNavigationGraph()
                        composable(route = NavigationScreen.Dashboard.getRoute()) {
                            DashboardScreen()
                        }
                        composable(
                            route = NavigationScreen.Config.UpdatedRequired.getRoute(),
                            enterTransition = { defaultScreenEnterTransition() },
                            exitTransition = { defaultScreenExitTransition() },
                            popEnterTransition = { defaultScreenPopEnterTransition() },
                        ) {
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
                        ConfigState.UpdateRequired -> LocalNavigationManager.current.navigate(NavigationScreen.Config.UpdatedRequired)
                    }
                }
            }
        }
    }
}
