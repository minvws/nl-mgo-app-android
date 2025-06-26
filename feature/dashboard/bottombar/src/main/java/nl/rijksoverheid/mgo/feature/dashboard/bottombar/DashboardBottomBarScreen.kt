package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackBar
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackBarVisuals
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary

object DashboardBottomBarScreenTestTag {
  const val SCREEN = "DashboardBottomBarScreen"
}

/**
 * Composable that shows the a screen with bottom bar. The dashboard screen is the root screen of the app that shows after inputting the
 * local pin code (apart from if the user has not completed the initial onboarding, then that will be shown first).
 *
 * @param overviewStartDestination The start destination of the overview tab.
 * @param overviewNavGraph The navigation graph of the overview tab.
 * @param organizationsStartDestination The start destination of the organizations tab.
 * @param organizationsNavGraph The navigation graph of the organizations tab.
 * @param settingsStartDestination The start destination of the settings tab.
 * @param settingsNavGraph The navigation graph of the settings tab.ß
 */
@Composable
fun DashboardBottomBarScreen(
  overviewStartDestination: Any,
  overviewNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
  organizationsStartDestination: Any,
  organizationsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
  settingsStartDestination: Any,
  settingsNavGraph: NavGraphBuilder.(navController: NavController) -> Unit,
) {
  hiltViewModel<DashboardBottomBarScreenViewModel>()
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

  val snackbarHostState = remember { SnackbarHostState() }
  val snackbarPresenter = LocalDashboardSnackbarPresenter.current

  LaunchedEffect(snackbarPresenter) {
    snackbarPresenter.snackbarVisuals.collectLatest {
      snackbarHostState.showSnackbar(it)
    }
  }

  Scaffold(
    modifier = Modifier.testTag(DashboardBottomBarScreenTestTag.SCREEN),
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState) {
        MgoSnackBar(
          visuals = it.visuals as MgoSnackBarVisuals,
          onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
        )
      }
    },
    content = { contentPadding ->
      HorizontalPager(
        modifier = Modifier.consumeWindowInsets(contentPadding).padding(contentPadding),
        state = pagerState,
        userScrollEnabled = false,
      ) { position ->
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
    contentColor = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
  ) {
    BottomBarItem.entries.forEachIndexed { index, item ->
      val isSelected = item.route == currentRoute
      NavigationBarItem(
        modifier = Modifier.testTag(item.testTag),
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
            selectedIconColor = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
            selectedTextColor = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
            unselectedIconColor = MaterialTheme.colorScheme.symbolsPrimary(),
            unselectedTextColor = MaterialTheme.colorScheme.symbolsPrimary(),
            indicatorColor = MaterialTheme.colorScheme.interactiveSecondaryDefaultBackground(),
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
