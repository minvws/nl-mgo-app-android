package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.feature.onboarding.OnboardingScreen
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MgoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val rootNavController = rememberNavController()
                    NavHost(
                        navController = rootNavController,
                        startDestination = NavigationScreen.Splash.getRoute(),
                    ) {
                        composable(route = NavigationScreen.Splash.getRoute()) {
                            OnboardingScreen()
                        }
                    }
                }
            }
        }
    }
}
