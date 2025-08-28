package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
  val viewModel: EditOverviewBottomSheetViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    EditOverviewBottomSheetContent(
      viewState = viewState,
      onSave = { categories ->
      },
      onClickHealthCategory = { categoryId, favorite ->
        viewModel.onClickListItem(categoryId, favorite)
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
  viewState: EditOverviewBottomSheetViewState,
  onSave: (categories: List<HealthCareCategory>) -> Unit,
  onClickHealthCategory: (categoryId: HealthCareCategoryId, favorite: Boolean) -> Unit,
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
    LazyColumn(modifier = Modifier.padding(innerPadding), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
      item {
        Text(text = stringResource(CopyR.string.edit_overview_favorites_heading), style = MaterialTheme.typography.headlineSmall)
      }
      if (viewState.favorites.isEmpty()) {
        item {
          FavoriteEmptyCard(modifier = Modifier.padding(top = 8.dp))
        }
      } else {
        item {
          FavoritesCard(
            modifier = Modifier.padding(top = 8.dp).animateItem(),
            favorites = viewState.favorites,
            onClickHealthCategory = onClickHealthCategory,
          )
        }
      }

      item {
        Text(modifier = Modifier.padding(top = 24.dp), text = "Categorieën", style = MaterialTheme.typography.headlineSmall)
      }

      item {
        CategoriesCard(
          modifier = Modifier.padding(top = 8.dp).animateItem(),
          categories = viewState.categories,
          onClickHealthCategory = onClickHealthCategory,
        )
      }
    }
  }
}

@Composable
private fun FavoriteEmptyCard(modifier: Modifier = Modifier) {
  MgoCard(modifier = modifier) {
    Text(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      text = stringResource(CopyR.string.edit_overview_favorites_empty),
      color = MaterialTheme.colorScheme.contentSecondary(),
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
private fun FavoritesCard(
  favorites: List<HealthCareCategoryId>,
  onClickHealthCategory: (categoryId: HealthCareCategoryId, favorite: Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    MgoCard {
      favorites.forEachIndexed { index, category ->
        HealthCategoryListItem(
          category = category,
          state = HealthCategoryListItemState.REMOVE,
          onClick = {
            onClickHealthCategory(category, false)
          },
          draggable = true,
          hasDivider = index != favorites.lastIndex,
        )
      }
    }
  }
}

@Composable
private fun CategoriesCard(
  categories: List<HealthCareCategoryId>,
  onClickHealthCategory: (categoryId: HealthCareCategoryId, favorite: Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    MgoCard {
      categories.forEachIndexed { index, category ->
        HealthCategoryListItem(
          category = category,
          state = HealthCategoryListItemState.ADD,
          onClick = {
            onClickHealthCategory(category, true)
          },
          draggable = false,
          hasDivider = index != categories.lastIndex,
        )
      }
    }
  }
}

@DefaultPreviews
@Composable
private fun EditOverviewBottomSheetNoFavoritesPreview() {
  MgoTheme {
    EditOverviewBottomSheetContent(
      viewState =
        EditOverviewBottomSheetViewState(
          favorites = listOf(),
          categories = HealthCareCategoryId.entries,
        ),
      onSave = {},
      onClickHealthCategory = { categoryId, favorite -> },
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
private fun EditOverviewBottomSheetFavoritesPreview() {
  MgoTheme {
    EditOverviewBottomSheetContent(
      viewState =
        EditOverviewBottomSheetViewState(
          favorites = listOf(HealthCareCategoryId.MEDICATIONS, HealthCareCategoryId.APPOINTMENTS),
          categories = HealthCareCategoryId.entries - HealthCareCategoryId.MEDICATIONS - HealthCareCategoryId.APPOINTMENTS,
        ),
      onSave = {},
      onClickHealthCategory = { categoryId, favorite -> },
      onNavigateBack = {},
    )
  }
}
