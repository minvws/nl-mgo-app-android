package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import kotlinx.coroutines.launch

@Composable
fun DashboardBottomBarScreen(
    overviewStartDestination: Any,
    overviewNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
    organizationsStartDestination: Any,
    organizationsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
    aboutThisAppStartDestination: Any,
    aboutThisAppNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomBarItems =
        remember {
            listOf(
                createOverviewBottomBarItem(),
                createOrganizationsBottomBarItem(),
                createAboutThisAppBottomBarItem(),
            )
        }
    val pagerState = rememberPagerState(pageCount = { bottomBarItems.size })

    MgoScaffold(
        contentPadding = PaddingValues(),
        content = {
            HorizontalPager(pagerState) { position ->
                val bottomBarItem = bottomBarItems[position]
                when (bottomBarItem.route) {
                    BottomBarItemNavigation.AboutThisApp -> {
                        val aboutThisAppNavController = rememberNavController()
                        NavHost(
                            navController = aboutThisAppNavController,
                            startDestination = aboutThisAppStartDestination,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                        ) {
                            aboutThisAppNavGraph(aboutThisAppNavController)
                        }
                    }

                    BottomBarItemNavigation.Organizations -> {
                        val organizationsNavController = rememberNavController()
                        NavHost(
                            navController = organizationsNavController,
                            startDestination = organizationsStartDestination,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                        ) {
                            organizationsNavGraph(organizationsNavController)
                        }
                    }

                    BottomBarItemNavigation.Overview -> {
                        val overviewNavController = rememberNavController()
                        NavHost(
                            navController = overviewNavController,
                            startDestination = overviewStartDestination,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                        ) {
                            overviewNavGraph(overviewNavController)
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                items = bottomBarItems,
                currentRoute = bottomBarItems[pagerState.currentPage].route,
                onClickItem = { position ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(position)
                    }
                },
            )
        },
    )
}

@Composable
private fun BottomNavigationBar(
    items: List<BottomBarItem>,
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
        items.forEachIndexed { index, item ->
            val isSelected = false
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
                selected = item.route == currentRoute,
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

// @DefaultPreviews
// @Composable
// internal fun DashboardBottomBarScreenPreview() {
//    MgoTheme {
//        val navController = rememberNavController()
//        BottomNavigationBar()
//    }
// }
