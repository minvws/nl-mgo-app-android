package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.mgo.SetCorrectStatusBarIconColor
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_ALLERGIES
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_GROUP_HEALTH
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import sh.calvin.reorderable.ReorderableColumn
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun EditOverviewBottomSheet(onDismissRequest: () -> Unit) {
  val viewModel: EditOverviewBottomSheetViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  LaunchedEffect(Unit) {
    viewModel.closeBottomSheet.collectLatest {
      sheetState.hide()
      onDismissRequest()
    }
  }

  ModalBottomSheet(
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    SetCorrectStatusBarIconColor()
    EditOverviewBottomSheetContent(
      viewState = viewState,
      onClickSave = { favorites, nonFavorites -> viewModel.save(favorites = favorites, nonFavorites = nonFavorites) },
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
  onClickSave: (favorites: List<HealthCategoryGroup.HealthCategory>, nonFavorites: List<HealthCategoryGroup>) -> Unit,
  onNavigateBack: () -> Unit,
) {
  var favorites by remember { mutableStateOf(viewState.favorites) }
  var nonFavorites by remember { mutableStateOf(viewState.nonFavorites) }

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
            { onClickSave(favorites, nonFavorites) },
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.CategoriesRijkslint()),
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
      if (favorites.isEmpty()) {
        item {
          FavoriteEmptyCard(modifier = Modifier.padding(top = 8.dp))
        }
      } else {
        item {
          FavoritesCard(
            modifier = Modifier.padding(top = 8.dp).animateItem(),
            favorites = favorites,
            onClickHealthCategory = { category ->
              val group = viewState.groups.first { group -> group.categories.contains(category) }
              favorites = favorites.toMutableList().also { it.remove(category) }
              nonFavorites =
                nonFavorites.map { nonFavoriteGroup ->
                  if (nonFavoriteGroup.id == group.id) {
                    nonFavoriteGroup.copy(
                      categories =
                        nonFavoriteGroup.categories
                          .toMutableList()
                          .also { it.add(category) }
                          .sortedBy { group.categories.indexOf(it) },
                    )
                  } else {
                    nonFavoriteGroup
                  }
                }
            },
            onReorderFavorites = { fromIndex, toIndex ->
              val updatedFavorites = favorites.toMutableList()
              val item = updatedFavorites.removeAt(fromIndex)
              updatedFavorites.add(toIndex, item)
              favorites = updatedFavorites
            },
          )
        }
      }

      nonFavorites.forEach { group ->
        if (group.categories.isNotEmpty()) {
          item {
            Text(
              modifier = Modifier.padding(top = 24.dp),
              text = LocalContext.current.getString(group.heading),
              style = MaterialTheme.typography.headlineSmall,
            )
          }

          item {
            CategoriesCard(
              modifier = Modifier.padding(top = 8.dp).animateItem(),
              categories = group.categories,
              onClickHealthCategory = { category, _ ->
                favorites = favorites.toMutableList().also { it.add(category) }
                nonFavorites = nonFavorites.map { group -> group.copy(categories = group.categories.filter { it != category }) }
              },
            )
          }
        }
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
      color = MaterialTheme.colorScheme.LabelsSecondary(),
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Composable
private fun FavoritesCard(
  favorites: List<HealthCategoryGroup.HealthCategory>,
  onClickHealthCategory: (category: HealthCategoryGroup.HealthCategory) -> Unit,
  onReorderFavorites: (fromIndex: Int, toIndex: Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  val view = LocalView.current
  MgoCard(modifier = modifier) {
    ReorderableColumn(
      list = favorites,
      onSettle = onReorderFavorites,
      onMove = {
        ViewCompat.performHapticFeedback(
          view,
          HapticFeedbackConstantsCompat.SEGMENT_FREQUENT_TICK,
        )
      },
    ) { _, item, isDragging ->
      key(item) {
        ReorderableItem {
          val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
          Surface(shadowElevation = elevation, shape = if (isDragging) CardDefaults.shape else RectangleShape) {
            HealthCategoryListItem(
              category = item,
              state = HealthCategoryListItemState.REMOVE,
              onClick = {
                onClickHealthCategory(item)
              },
              dragHandleModifier =
                Modifier.draggableHandle(
                  onDragStarted = {
                    ViewCompat.performHapticFeedback(
                      view,
                      HapticFeedbackConstantsCompat.GESTURE_START,
                    )
                  },
                  onDragStopped = {
                    ViewCompat.performHapticFeedback(
                      view,
                      HapticFeedbackConstantsCompat.GESTURE_END,
                    )
                  },
                ),
              hasDivider = !isDragging && favorites.indexOf(item) != favorites.lastIndex,
            )
          }
        }
      }
    }
  }
}

@Composable
private fun CategoriesCard(
  categories: List<HealthCategoryGroup.HealthCategory>,
  onClickHealthCategory: (category: HealthCategoryGroup.HealthCategory, favorite: Boolean) -> Unit,
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
          hasDivider = index != categories.lastIndex,
        )
      }
    }
  }
}

@DefaultPreviews
@Composable
internal fun EditOverviewBottomSheetNoFavoritesPreview() {
  MgoTheme {
    EditOverviewBottomSheetContent(
      viewState =
        EditOverviewBottomSheetViewState(
          groups = listOf(),
          favorites = listOf(),
          nonFavorites = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH),
        ),
      onClickSave = { _, _ -> },
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun EditOverviewBottomSheetFavoritesPreview() {
  MgoTheme {
    EditOverviewBottomSheetContent(
      viewState =
        EditOverviewBottomSheetViewState(
          groups = listOf(),
          favorites = listOf(TEST_HEALTH_CATEGORY_PROBLEMS),
          nonFavorites = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH.copy(categories = listOf(TEST_HEALTH_CATEGORY_ALLERGIES))),
        ),
      onClickSave = { _, _ -> },
      onNavigateBack = {},
    )
  }
}
