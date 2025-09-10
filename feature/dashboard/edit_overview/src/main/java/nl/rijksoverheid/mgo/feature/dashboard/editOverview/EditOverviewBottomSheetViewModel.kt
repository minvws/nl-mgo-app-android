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
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class EditOverviewBottomSheetViewModel
  @Inject
  constructor(
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val healthCareCategoryRepository: HealthCareCategoriesRepository,
  ) : ViewModel() {
    private val initialCategories = runBlocking(ioDispatcher) { healthCareCategoryRepository.observe().first() }
    private val initialState =
      EditOverviewBottomSheetViewState(
        favorites =
          initialCategories
            .filter { category ->
              category.favoritePosition != -1
            }.sortedBy { category -> category.favoritePosition }
            .map { category -> category.id },
        nonFavorites = initialCategories.filter { category -> category.favoritePosition == -1 }.map { category -> category.id },
      )
    private val _viewState = MutableStateFlow<EditOverviewBottomSheetViewState>(initialState)
    val viewState = _viewState.asStateFlow()

    private val _closeBottomSheet = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val closeBottomSheet = _closeBottomSheet.asSharedFlow()

    fun clickFavorite(
      categoryId: HealthCareCategoryId,
      favorite: Boolean,
    ) {
      viewModelScope.launch {
        _viewState.update { viewState ->
          if (favorite) {
            viewState.copy(
              favorites = viewState.favorites.toMutableList().also { it.add(categoryId) },
              nonFavorites = viewState.nonFavorites.toMutableList().also { it.remove(categoryId) },
            )
          } else {
            viewState.copy(
              favorites = viewState.favorites.toMutableList().also { it.remove(categoryId) },
              nonFavorites =
                viewState.nonFavorites
                  .toMutableList()
                  .also { it.add(categoryId) }
                  .sortedBy { HealthCareCategoryId.entries.indexOf(it) },
            )
          }
        }
      }
    }

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

    fun save() {
      viewModelScope.launch {
        val favorites = _viewState.value.favorites
        healthCareCategoryRepository.setFavorites(favorites)
        _closeBottomSheet.tryEmit(Unit)
      }
    }

    fun onClear() {
      _viewState.tryEmit(initialState)
    }
  }
