package nl.rijksoverheid.mgo

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.snackbar.DefaultLocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
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
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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

      val textSelectionColors =
        TextSelectionColors(
          handleColor = Color.Red,
          backgroundColor = Color.Red,
        )

      CompositionLocalProvider(
        LocalDashboardSnackbarPresenter provides DefaultLocalDashboardSnackbarPresenter(),
        LocalAppThemeProvider provides DefaultLocalAppThemeProvider(appTheme),
        LocalTextSelectionColors provides textSelectionColors,
      ) {
        val isDarkTheme = LocalAppThemeProvider.current.appTheme.isDarkTheme()
        MgoTheme(modifier = Modifier.fillMaxSize(), isDarkTheme = isDarkTheme) {
          val startDestination = remember { viewModel.getStartDestination() }
          val navController = rememberNavController()

          val textSelectionColors =
            TextSelectionColors(
              handleColor = MaterialTheme.colorScheme.ActionsGhostText(),
              backgroundColor = MaterialTheme.colorScheme.ActionsGhostText().copy(alpha = 0.2f),
            )
          CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
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
            DeviceRootedDialog(show = false)

            // Show a dialog if user takes a screenshot
            HandleScreenshotDetection()

            // Set correct status bar icon colors for selected theme
            LaunchedEffect(isDarkTheme) {
              WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme
            }
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
        automaticLocalisationEnabled = false,
        fromOnboarding = !viewModel.isDigidAuthenticated(),
      )
      addDigidNavGraph(navController = navController, keyValueStore = viewModel.keyValueStore)
    }
  }

  @Composable
  private fun CheckAppLock(viewModel: MainViewModel) {
    LaunchedEffect(Unit) {
      viewModel.appLifecycleRepository.observeLifecycle().collectLatest { state ->
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

  @Composable
  private fun HandleScreenshotDetection() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
      var showDialog by remember { mutableStateOf(false) }
      if (showDialog) {
        MgoAlertDialog(
          heading = stringResource(CopyR.string.screenshotalert_heading),
          subHeading = stringResource(CopyR.string.screenshotalert_subheading),
          positiveButtonText = stringResource(CopyR.string.screenshotalert_action),
          positiveButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
          onClickPositiveButton = { showDialog = false },
          onDismissRequest = { showDialog = false },
        )
      }

      val screenCaptureCallback =
        remember {
          ScreenCaptureCallback {
            showDialog = true
          }
        }

      val lifecycleOwner = LocalLifecycleOwner.current
      DisposableEffect(lifecycleOwner) {
        val observer =
          LifecycleEventObserver { _, event ->
            when (event) {
              Lifecycle.Event.ON_START -> {
                registerScreenCaptureCallback(mainExecutor, screenCaptureCallback)
              }
              Lifecycle.Event.ON_STOP -> {
                unregisterScreenCaptureCallback(screenCaptureCallback)
              }
              else -> {}
            }
          }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
          lifecycleOwner.lifecycle.removeObserver(observer)
        }
      }
    }
  }
}
