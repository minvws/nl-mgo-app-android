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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.snackbar.DefaultLocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.theme.DefaultLocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme
import nl.rijksoverheid.mgo.devicerooted.DeviceRootedDialog
import nl.rijksoverheid.mgo.lifecycle.AppLifecycleState
import nl.rijksoverheid.mgo.navigation.dashboard.addDashboardNavGraph
import nl.rijksoverheid.mgo.navigation.digid.addDigidNavGraph
import nl.rijksoverheid.mgo.navigation.localisation.addLocalisationNavGraph
import nl.rijksoverheid.mgo.navigation.onboarding.addOnboardingNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeCreateNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeLoginNavGraph

/**
 * The app has a single activity architecture, which means this is the entry point to the app and only activity.
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      val viewModel: MainViewModel = hiltViewModel()
      val appTheme by viewModel.appTheme.collectAsStateWithLifecycle()

      CompositionLocalProvider(
        LocalDashboardSnackbarPresenter provides DefaultLocalDashboardSnackbarPresenter(),
        LocalAppThemeProvider provides DefaultLocalAppThemeProvider(appTheme),
      ) {
        val isDarkTheme = LocalAppThemeProvider.current.appTheme.isDarkTheme()
        MgoTheme(modifier = Modifier.fillMaxSize(), isDarkTheme = isDarkTheme) {
          val startDestination = remember { viewModel.getStartDestination() }
          val navController = rememberNavController()

          // The main navigation
          RootNavigation(
            navController = navController,
            startDestination = startDestination,
            viewModel = viewModel,
          )

          // Set if taking screenshots is enabled or not
          CheckFlagSecure(viewModel = viewModel)

          // Check if the app needs to be locked (show pin code screen above current screen)
          CheckAppLock(viewModel = viewModel)

          // Handle navigating to a dialog to display
          HandleNavigateDialog(viewModel = viewModel, navController = navController)

          // Device rooted dialog
          DeviceRootedDialog(show = viewModel.showDeviceRootedDialog())

          // Set correct status bar icon colors for selected theme
          LaunchedEffect(isDarkTheme) {
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme
          }
        }
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
      addDashboardNavGraph(rootNavController = navController, mainViewModel = viewModel)
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
