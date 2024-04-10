package nl.rijksoverheid.mgo.feature.addhealthcare.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen() {
    val navigationManager: NavigationManager = LocalNavigationManager.current
    val viewModel: SearchScreenViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        delay(3000)
        viewModel.setName("d")
        viewModel.setCity("enschede")
        viewModel.getSearchResults()
    }

    LaunchedEffect("navigation") {
        viewModel.navigation.collectLatest { screen ->
            navigationManager.navigate(screen)
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Red),
    )
}
