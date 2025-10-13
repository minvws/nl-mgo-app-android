package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryId
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class EditOverviewBottomSheetViewModel
  @Inject
  constructor(
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val favoriteRepository: FavoriteHealthCategoriesRepository,
  ) : ViewModel() {
    private val groups = runBlocking(ioDispatcher) { getHealthCategoriesFromDisk() }
    private val initialFavorites = runBlocking(ioDispatcher) { favoriteRepository.observe().first() }
    private val initialState =
      EditOverviewBottomSheetViewState(
        groups = groups,
        favorites = groups.getFavorites(initialFavorites),
        nonFavorites = groups.filterFavorites(initialFavorites),
      )
    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _closeBottomSheet = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val closeBottomSheet = _closeBottomSheet.asSharedFlow()

    fun save(
      favorites: List<HealthCategoryGroup.HealthCategory>,
      nonFavorites: List<HealthCategoryGroup>,
    ) {
      viewModelScope.launch(ioDispatcher) {
        favoriteRepository.store(
          favorites = favorites.map { favorite -> favorite.id },
        )
        _viewState.update { viewState -> viewState.copy(favorites = favorites, nonFavorites = nonFavorites) }
        _closeBottomSheet.tryEmit(Unit)
      }
    }

    private fun List<HealthCategoryGroup>.filterFavorites(favorites: List<HealthCategoryId>): List<HealthCategoryGroup> =
      this.map { group -> group.copy(categories = group.categories.filter { category -> !favorites.contains(category.id) }) }

    private fun List<HealthCategoryGroup>.getFavorites(favorites: List<HealthCategoryId>): List<HealthCategoryGroup.HealthCategory> =
      favorites.mapNotNull { categoryId -> this.map { group -> group.categories }.flatten().firstOrNull { it.id == categoryId } }
  }
