package nl.rijksoverheid.mgo.data.healthCategories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryId
import javax.inject.Inject
import javax.inject.Singleton

private val KEY_FAVORITE_HEALTH_CARE_CATEGORIES = stringPreferencesKey("favorites")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "healthCategories")

@Singleton
class FavoriteHealthCategoriesRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) {
    fun observe(): Flow<List<HealthCategoryId>> =
      context.dataStore.data
        .map { preferences ->
          preferences[KEY_FAVORITE_HEALTH_CARE_CATEGORIES]
            ?.split(",")
            ?: emptyList()
        }

    fun store(favorites: List<HealthCategoryId>) =
      runBlocking {
        context.dataStore.edit { preferences ->
          preferences[KEY_FAVORITE_HEALTH_CARE_CATEGORIES] = favorites.joinToString(",")
        }
      }
  }
