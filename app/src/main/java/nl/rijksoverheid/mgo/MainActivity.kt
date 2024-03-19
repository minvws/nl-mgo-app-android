package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.feature.splash.SplashScreen
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MgoTheme {
                val rootNavController = rememberNavController()
                NavHost(
                    navController = rootNavController,
                    startDestination = NavigationScreen.Splash.getRoute(),
                ) {
                    composable(route = NavigationScreen.Splash.getRoute()) {
                        SplashScreen()
                    }
                }
            }
        }
    }
}
