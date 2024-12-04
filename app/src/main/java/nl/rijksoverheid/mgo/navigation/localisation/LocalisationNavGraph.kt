package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreen
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreenViewModel
import nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic.OrganizationListAutomaticSearchScreen
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.OrganizationListManualScreen
import nl.rijksoverheid.mgo.navigation.getViewModel
import nl.rijksoverheid.mgo.navigation.mgoComposable

fun NavGraphBuilder.addLocalisationNavGraph(
    navController: NavController,
    automaticLocalisationEnabled: Boolean,
) {
    val startNavigation =
        if (automaticLocalisationEnabled) LocalisationNavigation.OrganizationListAutomatic else LocalisationNavigation.AddOrganization
    navigation<LocalisationNavigation.Root>(startNavigation) {
        mgoComposable<LocalisationNavigation.AddOrganization> {
            AddOrganizationScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToOrganizationSearch = { name, city ->
                    navController.navigate(LocalisationNavigation.OrganisationListManual(name = name, city = city))
                },
            )
        }

        mgoComposable<LocalisationNavigation.OrganisationListManual> { backStackEntry ->
            val route = backStackEntry.toRoute<LocalisationNavigation.OrganisationListManual>()
            val addOrganizationScreenViewModel =
                navController.getViewModel<AddOrganizationScreenViewModel>(
                    route = LocalisationNavigation.AddOrganization,
                )
            OrganizationListManualScreen(
                name = route.name,
                city = route.city,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddOrganization = {
                    addOrganizationScreenViewModel?.setName("")
                    addOrganizationScreenViewModel?.setCity("")
                    navController.popBackStack(route = LocalisationNavigation.AddOrganization, inclusive = false)
                },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = LocalisationNavigation.AddOrganization,
                        inclusive = true,
                    )
                },
            )
        }

        mgoComposable<LocalisationNavigation.OrganizationListAutomatic> {
            OrganizationListAutomaticSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = LocalisationNavigation.OrganizationListAutomatic,
                        inclusive = true,
                    )
                },
            )
        }
    }
}
