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
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.navigation.BottomBarNavigationScreen
import nl.rijksoverheid.mgo.navigation.LocalRootNavigationManager
import nl.rijksoverheid.mgo.navigation.ProvideNavigationManager
import nl.rijksoverheid.mgo.navigation.RootNavigationManager
import nl.rijksoverheid.mgo.navigation.RootNavigationScreen
import nl.rijksoverheid.mgo.navigation.addBottomBarNavGraph
import nl.rijksoverheid.mgo.navigation.addLocalisationNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MgoTheme(modifier = Modifier.fillMaxSize()) {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination = BottomBarNavigationScreen.Start.getRoute()
                val navController = rememberNavController()
                val navigationManager = RootNavigationManager(navController = navController)
                ProvideNavigationManager(navigationManager = navigationManager) {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        addBottomBarNavGraph(navController = navController)
                        addLocalisationNavGraph(navController = navController)
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
