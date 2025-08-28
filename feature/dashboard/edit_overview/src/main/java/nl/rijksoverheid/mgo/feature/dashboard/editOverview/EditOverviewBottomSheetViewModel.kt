package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import javax.inject.Inject

@HiltViewModel
class EditOverviewBottomSheetViewModel
  @Inject
  constructor(
    private val healthCareCategoryRepository: HealthCareCategoriesRepository,
  ) : ViewModel() {
    private val initialCategories = runBlocking { healthCareCategoryRepository.observe().first() }
    private val initialState =
      EditOverviewBottomSheetViewState(
        favorites = initialCategories.filter { category -> category.favorite }.map { category -> category.id },
        categories = initialCategories.filter { category -> category.favorite == false }.map { category -> category.id },
      )
    private val _viewState = MutableStateFlow<EditOverviewBottomSheetViewState>(initialState)
    val viewState = _viewState.asStateFlow()

    fun onClickListItem(
      categoryId: HealthCareCategoryId,
      favorite: Boolean,
    ) {
      viewModelScope.launch {
        _viewState.update { viewState ->
          if (favorite) {
            viewState.copy(
              favorites = viewState.favorites.toMutableList().also { it.add(categoryId) },
              categories = viewState.categories.toMutableList().also { it.remove(categoryId) },
            )
          } else {
            viewState.copy(
              favorites = viewState.favorites.toMutableList().also { it.remove(categoryId) },
              categories = viewState.categories.toMutableList().also { it.add(categoryId) },
            )
          }
        }
      }
    }
  }
