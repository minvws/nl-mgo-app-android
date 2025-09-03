package nl.rijksoverheid.mgo.data.healthcare.mgoResource.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
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
    private val json = Json

    /**
     * Observes all [HealthCareCategory]. Updates when the favorite status of a [HealthCareCategory] changes.
     */
    override fun observe(): Flow<List<HealthCareCategory>> =
      keyValueStore.observeString(KEY_FAVORITE_HEALTH_CARE_CATEGORIES).map { favoritesJson ->
        val favorites: List<String> = json.decodeFromString(favoritesJson ?: "[]")
        if (favorites.isEmpty()) {
          HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favoritePosition = -1) }
        } else {
          HealthCareCategoryId.entries.map { id ->
            HealthCareCategory(
              id = id,
              favoritePosition = favorites.indexOf(id.toString()),
            )
          }
        }
      }

    /**
     * Marks a list of [HealthCareCategory] as favorite.
     *
     * @param categories The list of [HealthCareCategoryId] you want to mark as favorite.
     */
    override suspend fun setFavorites(categories: List<HealthCareCategoryId>) {
      val json = json.encodeToString(categories)
      keyValueStore.setString(KEY_FAVORITE_HEALTH_CARE_CATEGORIES, json)
    }
  }
