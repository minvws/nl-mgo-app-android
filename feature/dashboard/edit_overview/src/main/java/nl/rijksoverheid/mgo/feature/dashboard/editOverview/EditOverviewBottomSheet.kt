package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.supportRijkslint
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.TEST_HEALTH_CARE_CATEGORIES
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun EditOverviewBottomSheet(onDismissRequest: () -> Unit) {
  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    EditOverviewBottomSheetContent(
      onSave = { categories ->
      },
      onNavigateBack = {
        coroutineScope.launch {
          sheetState.hide()
          onDismissRequest()
        }
      },
    )
  }
}

@Composable
private fun EditOverviewBottomSheetContent(
  onSave: (categories: List<HealthCareCategory>) -> Unit,
  onNavigateBack: () -> Unit,
) {
  Scaffold(
    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95f),
    topBar = {
      MgoTopAppBar(
        title = stringResource(CopyR.string.edit_overview_heading),
        onNavigateBack = onNavigateBack,
        windowInsets = WindowInsets(0),
        navigationIcon = Icons.Default.Close,
        actions = {
          TextButton(
            { onSave(TEST_HEALTH_CARE_CATEGORIES) },
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.supportRijkslint()),
          ) {
            Text(text = stringResource(CopyR.string.edit_overview_save), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
          }
        },
      )
    },
  ) { innerPadding ->
    LazyColumn(modifier = Modifier.padding(innerPadding), contentPadding = PaddingValues(horizontal = 16.dp)) {
      item {
        Text(text = stringResource(CopyR.string.edit_overview_favorites_heading), style = MaterialTheme.typography.headlineSmall)
        FavoriteCard(modifier = Modifier.padding(top = 8.dp), favorites = listOf())
      }
    }
  }
}

@Composable
private fun FavoriteCard(
  modifier: Modifier = Modifier,
  favorites: List<HealthCareCategory>,
) {
  MgoCard(modifier = modifier) {
    Text(
      modifier = Modifier.padding(16.dp),
      text = stringResource(CopyR.string.edit_overview_favorites_empty),
      color = MaterialTheme.colorScheme.contentSecondary(),
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@DefaultPreviews
@Composable
private fun EditOverviewBottomSheetPreview() {
  MgoTheme {
    EditOverviewBottomSheetContent(
      onSave = {},
      onNavigateBack = {},
    )
  }
}
