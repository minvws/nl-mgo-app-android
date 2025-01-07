package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DashboardBottomBarScreen(
    overviewStartDestination: Any,
    overviewNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
    organizationsStartDestination: Any,
    organizationsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
    settingsStartDestination: Any,
    settingsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
) {
    val viewModel: DashboardBottomBarScreenViewModel = hiltViewModel()
    DashboardBottomBarScreenContent(
        overviewStartDestination = overviewStartDestination,
        overviewNavGraph = overviewNavGraph,
        organizationsStartDestination = organizationsStartDestination,
        organizationsNavGraph = organizationsNavGraph,
        settingsStartDestination = settingsStartDestination,
        settingsNavGraph = settingsNavGraph,
    )
}

@Composable
fun DashboardBottomBarScreenContent(
    overviewStartDestination: Any,
    overviewNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
    organizationsStartDestination: Any,
    organizationsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
    settingsStartDestination: Any,
    settingsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
) {
    val navigateToRootOfGraph = remember { MutableSharedFlow<Int>(extraBufferCapacity = 1) }
    val coroutineScope = rememberCoroutineScope()
    val bottomBarItems = BottomBarItem.entries
    val pagerState = rememberPagerState(pageCount = { bottomBarItems.size })

    nl.rijksoverheid.mgo.component.mgo.MgoScaffold(
        horizontalPadding = 0.dp,
        content = {
            HorizontalPager(state = pagerState, userScrollEnabled = false) { position ->
                val bottomBarItem = bottomBarItems[position]
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    // Navigate to the root of nav controller if requested
                    navigateToRootOfGraph.collectLatest {
                        if (position == it) {
                            navController.navigate(navController.graph.findStartDestination().id)
                        }
                    }
                }
                val startDestination =
                    when (bottomBarItem.route) {
                        BottomBarItemNavigation.Settings -> settingsStartDestination
                        BottomBarItemNavigation.Organizations -> organizationsStartDestination
                        BottomBarItemNavigation.Overview -> overviewStartDestination
                    }
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                ) {
                    when (bottomBarItem.route) {
                        BottomBarItemNavigation.Settings -> settingsNavGraph(navController)
                        BottomBarItemNavigation.Organizations -> organizationsNavGraph(navController)
                        BottomBarItemNavigation.Overview -> overviewNavGraph(navController)
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = bottomBarItems[pagerState.currentPage].route,
                onClickItem = { position ->
                    // If we are selecting a different item, navigate to that screen
                    val isDifferentItem = position != pagerState.currentPage
                    if (isDifferentItem) {
                        coroutineScope.launch {
                            pagerState.scrollToPage(position)
                        }
                        return@BottomNavigationBar
                    }

                    // If re selecting the item, navigate to the root of that nav controller
                    navigateToRootOfGraph.tryEmit(pagerState.currentPage)
                },
            )
        },
    )
}

@Composable
private fun BottomNavigationBar(
    currentRoute: BottomBarItemNavigation,
    onClickItem: (position: Int) -> Unit,
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
        BottomBarItem.entries.forEachIndexed { index, item ->
            val isSelected = item.route == currentRoute
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
                    onClickItem(index)
                },
                colors =
                    NavigationBarItemDefaults.colors(
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
        BottomNavigationBar(
            currentRoute = BottomBarItemNavigation.Overview,
            onClickItem = {},
        )
    }
}
