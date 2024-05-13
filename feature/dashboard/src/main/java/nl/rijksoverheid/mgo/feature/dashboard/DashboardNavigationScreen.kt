package nl.rijksoverheid.mgo.feature.dashboard

import nl.rijksoverheid.mgo.framework.navigation.BaseNavigationScreen

sealed class DashboardNavigationScreen(name: String, placeholders: List<String> = listOf()) : BaseNavigationScreen(name, placeholders)
