package nl.rijksoverheid.mgo.feature.localisation.getsearchresults

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GetSearchResultsScreen() {
    val viewModel: GetSearchResultsScreenViewModel = hiltViewModel()
    viewModel.getSearchResults()
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue))
}
