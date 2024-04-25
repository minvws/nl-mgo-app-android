package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.feature.config.UpdateRequiredScreen
import nl.rijksoverheid.mgo.feature.dashboard.DashboardScreen
import nl.rijksoverheid.mgo.feature.localisation.addLocalisationNavigationGraph
import nl.rijksoverheid.mgo.feature.onboarding.addOnboardingNavigationGraph
import nl.rijksoverheid.mgo.framework.navigation.DefaultNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.ProvideNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MgoTheme(modifier = Modifier.fillMaxSize()) {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination =
                    if (viewModel.hasSeenOnboarding()) {
                        NavigationScreen.Dashboard.getRoute()
                    } else {
                        NavigationScreen.Onboarding.Start.getRoute()
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
                        addLocalisationNavigationGraph()
                        composable(route = NavigationScreen.Dashboard.getRoute()) {
                            DashboardScreen()
                        }
                        composableWithDefaultScreenTransitions(
                            route = NavigationScreen.Config.UpdatedRequired.getRoute(),
                        ) {
                            UpdateRequiredScreen(packageName = packageName)
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
