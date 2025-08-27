package nl.rijksoverheid.mgo.data.healthcare.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

class TestHealthCareCategoriesRepository : HealthCareCategoriesRepository {
  private val flow = MutableStateFlow<List<HealthCareCategory>>(HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favorite = false) })

  override fun observe(): Flow<List<HealthCareCategory>> = flow

  override suspend fun favorite(
    categoryId: HealthCareCategoryId,
    favorite: Boolean,
  ) {
    val updated =
      flow.value.map { category ->
        if (category.id == categoryId) {
          category.copy(favorite = favorite)
        } else {
          category
        }
      }
    flow.update { updated }
  }
}
