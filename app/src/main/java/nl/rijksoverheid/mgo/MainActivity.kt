package nl.rijksoverheid.mgo

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.snackbar.DefaultLocalSnackbarPresenter
import nl.rijksoverheid.mgo.component.theme.snackbar.LocalSnackbarPresenter
import nl.rijksoverheid.mgo.devicerooted.DeviceRootedDialog
import nl.rijksoverheid.mgo.feature.config.UpdateRequiredScreen
import nl.rijksoverheid.mgo.feature.pincode.login.PinCodeLoginScreen
import nl.rijksoverheid.mgo.navigation.config.ConfigNavigation
import nl.rijksoverheid.mgo.navigation.dashboard.addDashboardNavGraph
import nl.rijksoverheid.mgo.navigation.localisation.addLocalisationNavGraph
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.onboarding.addOnboardingNavGraph
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeNavigation
import nl.rijksoverheid.mgo.navigation.pincode.addPinCodeNavGraph
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
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        addOnboardingNavGraph(navController = navController)
                        addPinCodeNavGraph(navController = navController)
                        addDashboardNavGraph(rootNavController = navController)
                        addLocalisationNavGraph(navController = navController)
                        mgoComposable<ConfigNavigation.UpdateRequired> {
                            UpdateRequiredScreen()
                        }
                        mgoComposable<PinCodeNavigation.Login> {
                            BackHandler {
                                // TODO Quit app
                            }
                            PinCodeLoginScreen(
                                onNavigateForgotPin = {
                                    navController.navigate(PinCodeNavigation.Forgot)
                                },
                                onPinValidated = {
                                    navController.popBackStack()
                                },
                            )
                        }
                    }
                }

                // Device rooted dialog
                DeviceRootedDialog(show = viewModel.showDeviceRootedDialog())

                // Get timestamp when resuming app to see if we need to lock the app
                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    viewModel.getClosedAppTimestamp()
                }

                // Save timestamp when pausing app
                LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
                    viewModel.saveClosedAppTimestamp()
                }

                // Lock the app if requested
                LaunchedEffect(Unit) {
                    viewModel.lockAppFlow.collectLatest {
                        navController.navigate(PinCodeNavigation.Login) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}
