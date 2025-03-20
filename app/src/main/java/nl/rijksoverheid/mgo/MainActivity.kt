package nl.rijksoverheid.mgo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.mgo.snackbar.DefaultLocalSnackBarPresenter
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalSnackBarPresenter
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.devicerooted.DeviceRootedDialog
import nl.rijksoverheid.mgo.lifecycle.AppLifecycleState
import nl.rijksoverheid.mgo.navigation.dashboard.addDashboardNavGraph
import nl.rijksoverheid.mgo.navigation.digid.addDigidNavGraph
import nl.rijksoverheid.mgo.navigation.localisation.addLocalisationNavGraph
import nl.rijksoverheid.mgo.navigation.onboarding.addOnboardingNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeCreateNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeLoginNavGraph
import kotlinx.coroutines.flow.collectLatest

/**
 * The app has a single activity architecture, which means this is the entry point to the app and only activity.
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MgoTheme(modifier = Modifier.fillMaxSize()) {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination = viewModel.getStartDestination()
                val navController = rememberNavController()

                CheckFlagSecure(viewModel = viewModel)

                CompositionLocalProvider(LocalSnackBarPresenter provides DefaultLocalSnackBarPresenter()) {
                    RootNavigation(
                        navController = navController,
                        startDestination = startDestination,
                        viewModel = viewModel,
                    )
                }

                CheckAppLock(viewModel = viewModel)
                HandleNavigateDialog(viewModel = viewModel, navController = navController)

                // Device rooted dialog
                DeviceRootedDialog(show = viewModel.showDeviceRootedDialog())
            }
        }
    }

    @Composable
    private fun CheckFlagSecure(viewModel: MainViewModel) {
        LaunchedEffect(Unit) {
            viewModel.flagSecureFeatureToggle.collectLatest { enabled ->
                if (enabled) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE,
                    )
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }
        }
    }

    @Composable
    private fun RootNavigation(
        navController: NavHostController,
        startDestination: Any,
        viewModel: MainViewModel,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            addOnboardingNavGraph(navController = navController)
            addPinCodeCreateNavGraph(navController = navController)
            addPinCodeLoginNavGraph(navController = navController, activity = this@MainActivity)
            addDashboardNavGraph(rootNavController = navController)
            addLocalisationNavGraph(
                navController = navController,
                automaticLocalisationEnabled = viewModel.getAutomaticLocalisationEnabled(),
                fromOnboarding = !viewModel.isDigidAuthenticated(),
            )
            addDigidNavGraph(navController = navController, keyValueStore = viewModel.keyValueStore)
        }
    }

    @Composable
    private fun CheckAppLock(viewModel: MainViewModel) {
        val application = (LocalContext.current.applicationContext as MainApplication)
        LaunchedEffect(Unit) {
            application.appLifecycleState.collectLatest { state ->
                when (state) {
                    AppLifecycleState.FromBackground -> {
                        viewModel.checkAppLock()
                    }
                    AppLifecycleState.ToBackground -> {
                        viewModel.saveClosedAppTimestamp()
                    }
                }
            }
        }
    }

    @Composable
    private fun HandleNavigateDialog(
        viewModel: MainViewModel,
        navController: NavController,
    ) {
        LaunchedEffect(Unit) {
            viewModel.navigateDialog.collectLatest { screen ->
                navController.navigate(screen) {
                    launchSingleTop = true
                }
            }
        }
    }
}
