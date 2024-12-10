package nl.rijksoverheid.mgo.navigation.digid

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.digid.DigidLoginScreen
import nl.rijksoverheid.mgo.feature.digid.DigidMockScreen
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable

fun NavGraphBuilder.addDigidNavGraph(navController: NavController) {
    navigation<DigidNavigation.Root>(DigidNavigation.Login) {
        mgoComposable<DigidNavigation.Login> {
            DigidLoginScreen(
                onNavigateToDigidMock = {
                    navController.navigate(DigidNavigation.Mock)
                },
            )
        }

        mgoComposable<DigidNavigation.Mock> {
            DigidMockScreen(
                onNavigateToLocalisation = {
                    navController.navigate(LocalisationNavigation.Root) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
