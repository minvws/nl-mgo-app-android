package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import timber.log.Timber
import kotlin.enums.EnumEntries

@Composable
fun DashboardBottomBarScreen(
    navController: NavHostController,
    graph: NavGraphBuilder.() -> Unit,
    overviewTab: @Composable (navController: NavHostController) -> Unit,
    organizationsTab: @Composable (navController: NavHostController) -> Unit,
    aboutThisAppTab: @Composable () -> Unit,
) {
    val navControllersMap = mutableMapOf<Any, NavHostController>()
    val organizationsNavController = rememberNavController()
    val bottomBarItems = BottomBarItem.entries
    MgoScaffold(
        contentPadding = PaddingValues(),
        content = {
            NavHost(
                navController = navController,
                startDestination = BottomBarNavigation.Overview,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                builder = graph
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, items = bottomBarItems, navControllersMap = navControllersMap)
        },
    )
}

@Composable
private fun BottomNavigationBar(
    navController: NavController,
    items: EnumEntries<BottomBarItem>,
    navControllersMap: Map<Any, NavHostController>,
) {
    val bottomBarItemTextStyle =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        )
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.backgroundSecondary(),
        contentColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            NavigationBarItem(
                icon = {
                    val iconId =
                        if (isSelected) {
                            item.selectedIconId
                        } else {
                            item.deselectedIconId
                        }
                    Icon(painter = painterResource(id = iconId), contentDescription = null)
                },
                label = { Text(stringResource(item.titleId), style = bottomBarItemTextStyle) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                    selectedTextColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                    unselectedIconColor = MaterialTheme.colorScheme.iconsPrimary(),
                    unselectedTextColor = MaterialTheme.colorScheme.iconsPrimary(),
                    indicatorColor = MaterialTheme.colorScheme.actionSecondaryDefaultBackground(),
                ),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun DashboardBottomBarScreenPreview() {
    MgoTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController, items = BottomBarItem.entries, navControllersMap = mapOf())
    }
}
