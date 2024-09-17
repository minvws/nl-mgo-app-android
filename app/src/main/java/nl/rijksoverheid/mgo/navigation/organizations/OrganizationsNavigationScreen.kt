package nl.rijksoverheid.mgo.navigation.organizations

import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class OrganizationsNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) :
    NavigationScreen(name, placeholders) {
    data object Start : OrganizationsNavigationScreen(name = "organizations-start")
}
