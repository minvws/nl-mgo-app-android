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
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class EditOverviewBottomSheetViewModel
  @Inject
  constructor(
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val favoriteHealthCategoriesRepository: FavoriteHealthCategoriesRepository,
  ) : ViewModel() {
    private val groups = getHealthCategoriesFromDisk()
    private val initialFavorites = runBlocking(ioDispatcher) { favoriteHealthCategoriesRepository.observe().first() }
    private val initialState =
      EditOverviewBottomSheetViewState(
        favorites = groups.filter { category -> initialFavorites.contains(category.id) }.map { group -> group.categories }.flatten(),
        nonFavorites = groups.filter { category -> !initialFavorites.contains(category.id) }.map { group -> group.categories }.flatten(),
      )
    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _closeBottomSheet = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val closeBottomSheet = _closeBottomSheet.asSharedFlow()

    fun reorderFavorites(
      fromIndex: Int,
      toIndex: Int,
    ) {
      viewModelScope.launch {
        _viewState.update { viewState ->
          val updatedFavorites = viewState.favorites.toMutableList()
          val item = updatedFavorites.removeAt(fromIndex)
          updatedFavorites.add(toIndex, item)
          viewState.copy(favorites = updatedFavorites)
        }
      }
    }

    fun save(
      favorites: List<HealthCategoryGroup.HealthCategory>,
      nonFavorites: List<HealthCategoryGroup.HealthCategory>,
    ) {
      viewModelScope.launch {
        val favoriteIds = favorites.map { favorite -> favorite.id }
        favoriteHealthCategoriesRepository.store(favoriteIds)
        _viewState.update { viewState -> viewState.copy(favorites = favorites, nonFavorites = nonFavorites) }
        _closeBottomSheet.tryEmit(Unit)
      }
    }
  }
