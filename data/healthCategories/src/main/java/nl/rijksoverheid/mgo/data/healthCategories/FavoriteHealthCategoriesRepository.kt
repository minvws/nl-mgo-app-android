package nl.rijksoverheid.mgo.data.healthCategories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryId
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val KEY_FAVORITE_HEALTH_CARE_CATEGORIES = "KEY_FAVORITE_HEALTH_CARE_CATEGORIES"

@Singleton
class FavoriteHealthCategoriesRepository
  @Inject
  constructor(
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
  ) {
    fun observe(): Flow<List<HealthCategoryId>> =
      keyValueStorage
        .observe<String>(KEY_FAVORITE_HEALTH_CARE_CATEGORIES)
        .map {
          if (it.isNullOrBlank()) {
            emptyList()
          } else {
            it.split(",")
          }
        }

    fun store(favorites: List<HealthCategoryId>) {
      keyValueStorage.save(key = KEY_FAVORITE_HEALTH_CARE_CATEGORIES, value = favorites.joinToString(","))
    }
  }
