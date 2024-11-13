package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.snackbar.DefaultLocalSnackbarPresenter
import nl.rijksoverheid.mgo.component.theme.snackbar.LocalSnackbarPresenter
import nl.rijksoverheid.mgo.devicerooted.DeviceRootedDialog
import nl.rijksoverheid.mgo.navigation.dashboard.addDashboardNavGraph
import nl.rijksoverheid.mgo.navigation.localisation.addLocalisationNavGraph
import nl.rijksoverheid.mgo.navigation.onboarding.addOnboardingNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeCreateNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeLoginNavGraph
import kotlinx.coroutines.flow.collectLatest

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

                CompositionLocalProvider(LocalSnackbarPresenter provides DefaultLocalSnackbarPresenter()) {
                    RootNavigation(navController = navController, startDestination = startDestination)
                }

                CheckAppLock(viewModel = viewModel)
                HandleNavigateDialog(viewModel = viewModel, navController = navController)

                // Device rooted dialog
                DeviceRootedDialog(show = viewModel.showDeviceRootedDialog())
            }
        }
    }

    @Composable
    private fun RootNavigation(
        navController: NavHostController,
        startDestination: Any,
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
            addLocalisationNavGraph(navController = navController)
        }
    }

    @Composable
    private fun CheckAppLock(viewModel: MainViewModel) {
        // On every resume check if we need to lock the app
        LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
            viewModel.checkAppLock()
        }

        // On every pause save the timestamp so we know when coming back if to lock the app
        LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
            viewModel.saveClosedAppTimestamp()
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
