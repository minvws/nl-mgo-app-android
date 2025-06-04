package nl.rijksoverheid.mgo.navigation.pincode

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.MainActivity
import nl.rijksoverheid.mgo.feature.pincode.deleted.PinCodeDeletedScreen
import nl.rijksoverheid.mgo.feature.pincode.forgot.PinCodeForgotScreen
import nl.rijksoverheid.mgo.feature.pincode.login.PinCodeLoginScreen
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposableExt

/**
 * Adds all the navigation destinations that can be found when logging in with a pin code.
 * @param navController The nav controller used in this navigation.
 */
fun NavGraphBuilder.addPinCodeLoginNavGraph(
  navController: NavController,
  activity: MainActivity,
) {
  navigation<PinCodeLoginNavigation.Root>(PinCodeLoginNavigation.Login) {
    mgoComposableExt<PinCodeLoginNavigation.Login> {
      PinCodeLoginScreen(
        onNavigateForgotPin = {
          navController.navigate(PinCodeLoginNavigation.Forgot)
        },
        onPinValidated = {
          navController.navigate(DashboardNavigation.Root) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        },
      )
    }

    mgoComposableExt<PinCodeLoginNavigation.LoginDialog> {
      // This dialog is blocking, so quit the app if going back
      BackHandler {
        activity.finish()
      }

      PinCodeLoginScreen(
        onNavigateForgotPin = {
          navController.navigate(PinCodeLoginNavigation.Forgot)
        },
        onPinValidated = {
          navController.popBackStack()
        },
      )
    }

    mgoComposableExt<PinCodeLoginNavigation.Forgot> {
      PinCodeForgotScreen(
        onNavigateToPinCodeDeleted = {
          navController.navigate(PinCodeLoginNavigation.Deleted) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        },
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposableExt<PinCodeLoginNavigation.Deleted> {
      PinCodeDeletedScreen(
        onNavigateToPinCodeCreate = {
          navController.navigate(PinCodeCreateNavigation.Create) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        },
      )
    }
  }
}
