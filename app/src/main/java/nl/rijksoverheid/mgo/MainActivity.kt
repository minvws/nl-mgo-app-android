package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.feature.dashboard.DashboardScreen
import nl.rijksoverheid.mgo.feature.onboarding.addOnboardingNavigationGraph
import nl.rijksoverheid.mgo.feature.splash.SplashScreen
import nl.rijksoverheid.mgo.framework.navigation.DefaultNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.navigation.ProvideNavigationManager

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MgoTheme {
                val rootNavController = rememberNavController()
                val mainViewModel: MainActivityViewModel = hiltViewModel()
                ProvideNavigationManager(navigationManager = DefaultNavigationManager(navController = rootNavController)) {
                    val startDestination =
                        if (mainViewModel.hasSeenOnboarding()) {
                            NavigationScreen.Dashboard.getRoute()
                        } else {
                            NavigationScreen.Onboarding.Start.getRoute()
                        }
                    NavHost(
                        navController = rootNavController,
                        startDestination = startDestination,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        addOnboardingNavigationGraph()
                        composable(route = NavigationScreen.Splash.getRoute()) {
                            SplashScreen()
                        }
                        composable(route = NavigationScreen.Dashboard.getRoute()) {
                            DashboardScreen()
                        }
                    }
                }
            }
        }
    }
}
