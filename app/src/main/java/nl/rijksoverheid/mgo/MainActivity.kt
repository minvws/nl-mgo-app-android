package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.feature.config.UpdateRequiredScreen
import nl.rijksoverheid.mgo.feature.dashboard.DashboardScreen
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreen
import nl.rijksoverheid.mgo.feature.localisation.LocalisationScreen
import nl.rijksoverheid.mgo.feature.onboarding.OnboardingScreen
import nl.rijksoverheid.mgo.framework.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.LocalRootNavigationManager
import nl.rijksoverheid.mgo.navigation.ProvideNavigationManager
import nl.rijksoverheid.mgo.navigation.RootNavigationManager
import nl.rijksoverheid.mgo.navigation.RootNavigationScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MgoTheme(modifier = Modifier.fillMaxSize()) {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination =
                    if (viewModel.hasSeenOnboarding()) {
                        RootNavigationScreen.NewDashboard.BottomBar.getRoute()
                    } else {
                        RootNavigationScreen.Onboarding.getRoute()
                    }
                val navController = rememberNavController()
                val navigationManager = RootNavigationManager(navController = navController)
                ProvideNavigationManager(navigationManager = navigationManager) {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        composableWithDefaultScreenTransitions(route = RootNavigationScreen.Onboarding.getRoute()) {
                            OnboardingScreen(
                                onOnboardingFinished = {
                                    navigationManager.navigate(RootNavigationScreen.Dashboard)
                                },
                            )
                        }

                        composableWithDefaultScreenTransitions(route = RootNavigationScreen.NewDashboard.BottomBar.getRoute()) {
                            DashboardBottomBarScreen(
                                overviewScreen = {
                                    Box(modifier = Modifier.fillMaxSize().background(Color.Red))
                                },
                                aboutThisAppScreen = {
                                    Box(modifier = Modifier.fillMaxSize().background(Color.Blue))
                                },
                            )
                        }

                        composableWithDefaultScreenTransitions(route = RootNavigationScreen.Dashboard.getRoute()) {
                            DashboardScreen(
                                onNavigateToLocalisation = {
                                    navigationManager.navigate(RootNavigationScreen.Localisation)
                                },
                            )
                        }
                        composableWithDefaultScreenTransitions(route = RootNavigationScreen.Localisation.getRoute()) {
                            LocalisationScreen(
                                onLocalisationFinished = {
                                    navController.popBackStack(RootNavigationScreen.Dashboard.getRoute(), false)
                                },
                            )
                        }
                        composableWithDefaultScreenTransitions(
                            route = RootNavigationScreen.UpdatedRequired.getRoute(),
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
                        ConfigState.UpdateRequired -> LocalRootNavigationManager.current.navigate(RootNavigationScreen.UpdatedRequired)
                    }
                }
            }
        }
    }
}
