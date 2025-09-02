package nl.rijksoverheid.mgo.data.healthcare.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

class TestHealthCareCategoriesRepository : HealthCareCategoriesRepository {
  private val flow = MutableStateFlow<List<HealthCareCategory>>(HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favoritePosition = -1) })

  override fun observe(): Flow<List<HealthCareCategory>> = flow

  override suspend fun setFavorites(categories: List<HealthCareCategoryId>) {
    val updated =
      flow.value.map { category ->
        if (categories.contains(category.id)) {
          category.copy(favoritePosition = categories.indexOf(category.id))
        } else {
          category
        }
      }
    flow.update { updated }
  }
}
