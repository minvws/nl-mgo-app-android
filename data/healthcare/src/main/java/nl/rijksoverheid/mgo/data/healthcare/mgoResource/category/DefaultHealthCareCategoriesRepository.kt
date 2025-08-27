package nl.rijksoverheid.mgo.data.healthcare.mgoResource.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FAVORITE_HEALTH_CARE_CATEGORIES
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Repository that manages [HealthCareCategory].
 *
 * @param keyValueStore The encrypted [KeyValueStore] which is used to retrieve the favorite status of a [HealthCareCategoryId].
 */
internal class DefaultHealthCareCategoriesRepository
  @Inject
  constructor(
    @Named("secureKeyValueStore") private val keyValueStore: KeyValueStore,
  ) : HealthCareCategoriesRepository {
    /**
     * Observes all [HealthCareCategory]. Updates when the favorite status of a [HealthCareCategory] changes.
     */
    override fun observe(): Flow<List<HealthCareCategory>> =
      keyValueStore.observeStringSet(KEY_FAVORITE_HEALTH_CARE_CATEGORIES).map { favorites ->
        if (favorites.isNullOrEmpty()) {
          HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favorite = false) }
        } else {
          favorites
            .map { favorite ->
              HealthCareCategoryId.entries.map { id ->
                HealthCareCategory(
                  id = id,
                  favorite = favorite == id.toString(),
                )
              }
            }.flatten()
        }
      }

    /**
     * Mark this [HealthCareCategory] as a favorite.
     *
     * @param categoryId The [HealthCareCategoryId].
     * @param favorite True if you want to favorite, false if not.
     */
    override suspend fun favorite(
      categoryId: HealthCareCategoryId,
      favorite: Boolean,
    ) {
      val favorites = keyValueStore.getStringSet(KEY_FAVORITE_HEALTH_CARE_CATEGORIES)
      val updatedFavorites = mutableSetOf<String>()
      updatedFavorites.addAll(favorites ?: setOf())

      if (favorite) {
        updatedFavorites.add(categoryId.toString())
      } else {
        updatedFavorites.remove(categoryId.toString())
      }

      keyValueStore.setStringSet(KEY_FAVORITE_HEALTH_CARE_CATEGORIES, updatedFavorites)
    }
  }
